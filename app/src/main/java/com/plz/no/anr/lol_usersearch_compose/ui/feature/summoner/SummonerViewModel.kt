package com.plz.no.anr.lol_usersearch_compose.ui.feature.summoner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.feature.summoner.SummonerContract.*
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
) : BaseViewModel<UiState, Intent, SideEffect>() {

    private val summonerName: String? by lazy {
        stateHandle.get<String>(Route.Summoner.KEY_SUMMONER_NAME)
    }

    override fun setInitialState(): UiState = UiState.initial()

    override fun handleEvents(intent: Intent) {
        when (intent) {
            is Intent.OnLoad -> requestSummonerData()
            is Intent.Navigation.Back -> setEffect { SideEffect.Navigation.Back }
            is Intent.Spectator.OnWatch -> setEffect { SideEffect.Navigation.ToSpectator(intent.name)}
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