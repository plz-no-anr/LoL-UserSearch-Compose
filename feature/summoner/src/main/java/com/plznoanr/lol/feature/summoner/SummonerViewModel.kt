package com.plznoanr.lol.feature.summoner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.summoner.RequestSummonerUseCase
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.mvibase.MviViewModel
import com.plznoanr.lol.feature.summoner.navigation.SummonerArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummonerViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSummonerUseCase: RequestSummonerUseCase,
) : MviViewModel<UiState, Event, SideEffect>() {
    private val loadEvent = MutableSharedFlow<Unit>(extraBufferCapacity = Int.MAX_VALUE)

    private val summonerName: String = SummonerArgs(stateHandle).summonerName
    private val summonerTag: String = SummonerArgs(stateHandle).summonerTag

    override val uiState: StateFlow<UiState>

    private val summonerFlow = loadEvent.map {
        requestSummonerUseCase(summonerName to summonerTag).getOrNull()
    }

    init {
        val initialState = UiState()
        uiState = eventFlow.sendEvent {
            when (it) {
                is OnLoad -> onLoad()
                else -> return@sendEvent
            }
        }.toStateChangeFlow(initialState) { state, event ->
            when (event) {
                is OnLoad -> state.copy(isLoading = true)
                else -> state
            }
        }.combine(summonerFlow) { state, result ->
            if (result != null) {
                state.copy(
                    summoner = result,
                    isLoading = false
                )
            } else {
                state.copy(
                    errorMsg = "",
                    isLoading = false
                )
            }
        }.stateIn(
                scope = viewModelScope,
                initialValue = initialState,
                started = SharingStarted.Eagerly
            )
        viewModelScope.launch { onLoad() }
    }

    private suspend fun onLoad() {
        loadEvent.emit(Unit)
    }

    private infix fun String.to(tag: String): Nickname = Nickname(this, tag)

}

