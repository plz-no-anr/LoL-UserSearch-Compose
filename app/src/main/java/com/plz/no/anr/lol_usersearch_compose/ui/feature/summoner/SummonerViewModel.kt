package com.plz.no.anr.lol_usersearch_compose.ui.feature.summoner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.Route
import com.plznoanr.domain.usecase.summoner.RequestSummonerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummonerViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSummonerUseCase: RequestSummonerUseCase
) : BaseViewModel<SummonerContract.UiState, SummonerContract.Event, SummonerContract.Effect>() {

    private val summonerName: String? by lazy {
        stateHandle.get<String>(Route.Summoner.KEY_SUMMONER_NAME)
    }

    override fun setInitialState(): SummonerContract.UiState = SummonerContract.UiState.initial()

    override fun handleEvents(event: SummonerContract.Event) {
        when (event) {
            is SummonerContract.Event.OnLoad -> requestSummonerData()
            is SummonerContract.Event.Navigation.Back -> setEffect { SummonerContract.Effect.Navigation.Back }
            is SummonerContract.Event.Spectator.OnWatch -> setEffect { SummonerContract.Effect.Navigation.ToSpectator(event.name)}
        }
    }

    private fun requestSummonerData() {
        viewModelScope.launch {
            summonerName?.let {
                requestSummonerUseCase(it.trim())
                    .onStart { setState { copy(isLoading = true) } }
                    .collect { result ->
                        result.onSuccess {
                            setState { copy(data = it, isLoading = false) }
                        }.onFailure {
                            setState { copy(error = it.message, isLoading = false) }
                        }
                    }
            } ?: run {
                setState { copy(error = "Summoner name is null", isLoading = false) }
            }
        }
    }

}