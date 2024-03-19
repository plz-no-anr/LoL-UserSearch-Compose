package com.plznoanr.lol.feature.summoner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.common.model.Result
import com.plznoanr.lol.core.domain.usecase.summoner.GetSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.SaveBookmarkIdUseCase
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.mvibase.MviViewModel
import com.plznoanr.lol.feature.summoner.navigation.SummonerArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SummonerViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val getSummonerUseCase: GetSummonerUseCase,
    private val saveBookmarkIdUseCase: SaveBookmarkIdUseCase
) : MviViewModel<UiState, Event, SideEffect>() {

    private val summonerName: String = SummonerArgs(stateHandle).summonerName
    private val summonerTag: String = SummonerArgs(stateHandle).summonerTag

    override val uiState: StateFlow<UiState>

    private val summonerFlow: Flow<Result<Summoner>> = flow {
        emit(getSummonerUseCase(summonerName to summonerTag))
    }

    init {
        val initialState = UiState()
        uiState = eventFlow
            .onStart { emit(Event.OnInit) }
            .sendEvent {
                when (it) {
                    is Event.OnBookmark -> saveBookmarkIdUseCase(it.id)
                    is Event.OnWatch -> postEffect(NavigateToSpectator(it.summonerId))
                    is Event.OnBackClick -> postEffect(OnPopBack)
                    else -> return@sendEvent
                }
            }.reduce(initialState) { state -> state }
            .combine(summonerFlow) { state, result ->
                when (result) {
                    is Result.Success -> state.copy(
                        summoner = result.data,
                        isLoading = false
                    )

                    is Result.Error -> state.copy(
                        errorMsg = result.error.message,
                        isLoading = false
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = initialState,
                started = SharingStarted.Eagerly
            )
    }

    private infix fun String.to(tag: String): Nickname = Nickname(this, tag)

}

