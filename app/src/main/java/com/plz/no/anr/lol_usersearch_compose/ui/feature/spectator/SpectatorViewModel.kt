package com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.Route
import com.plznoanr.domain.usecase.spectator.RequestSpectatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpectatorViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSpectatorUseCase: RequestSpectatorUseCase
) : BaseViewModel<SpectatorContract.UiState, SpectatorContract.Event, SpectatorContract.Effect>() {

    private val summonerName: String? by lazy {
        stateHandle.get<String>(Route.Spectator.KEY_SUMMONER_NAME)
    }

    override fun setInitialState(): SpectatorContract.UiState = SpectatorContract.UiState.initial()

    override fun handleEvents(event: SpectatorContract.Event) {
        when (event) {
            is SpectatorContract.Event.Navigation.Back -> setEffect { SpectatorContract.Effect.Navigation.Back }
            is SpectatorContract.Event.OnLoad -> getSpectator()
        }
    }

    private fun getSpectator() {
        viewModelScope.launch {
            summonerName?.let {
                requestSpectatorUseCase(it.trim())
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