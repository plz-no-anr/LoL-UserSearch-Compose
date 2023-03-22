package com.plz.no.anr.lol_usersearch_compose.ui.feature.summoner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.Navigation
import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.usecase.summoner.RequestSummonerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SummonerContract: BaseContract() {

    data class UiState(
        val data: Summoner?,
        val isLoading: Boolean = false,
        val error: String? = null
    ): ViewState
    sealed class Event: ViewEvent {
        object OnLoad: Event()
        object Navigation {
            object Back: Event()
        }
    }

    sealed class Effect: ViewSideEffect {
        data class Toast(val msg: String): Effect()
        sealed class Navigation: Effect() {
            object Back: Navigation()
        }
    }

}

@HiltViewModel
class SummonerViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSummonerUseCase: RequestSummonerUseCase
): BaseViewModel<SummonerContract.UiState, SummonerContract.Event, SummonerContract.Effect>() {

    private val summonerName: String by lazy {
        stateHandle.get<String>(Navigation.Args.SUMMONER_NAME) ?: ""
    }
    override fun setInitialState(): SummonerContract.UiState = SummonerContract.UiState(
        data = null
    )

    override fun handleEvents(event: SummonerContract.Event) {
        when (event) {
            is SummonerContract.Event.OnLoad -> {
                requestSummonerData()
            }
            is SummonerContract.Event.Navigation.Back -> {
                setEffect { SummonerContract.Effect.Navigation.Back }
            }
        }
    }

    private fun requestSummonerData() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            requestSummonerUseCase(summonerName.trim()).collectLatest { result ->
                result.onSuccess {
                    setState { copy(data = it, isLoading = false) }
                }.onFailure {
                    setState { copy(error = it.message, isLoading = false) }
                }
            }
        }
    }

}