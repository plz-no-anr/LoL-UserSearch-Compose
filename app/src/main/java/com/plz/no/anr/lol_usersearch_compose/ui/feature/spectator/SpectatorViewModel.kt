package com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.Navigation
import com.plznoanr.domain.usecase.spectator.RequestSpectatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpectatorViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSpectatorUseCase: RequestSpectatorUseCase
): BaseViewModel<SpectatorContract.UiState, SpectatorContract.Event, SpectatorContract.Effect>() {

    private val summonerName: String by lazy {
        stateHandle.get<String>(Navigation.Args.SUMMONER_NAME) ?: ""
    }
    override fun setInitialState(): SpectatorContract.UiState = SpectatorContract.UiState(
        data = null
    )

    override fun handleEvents(event: SpectatorContract.Event) {
        when (event) {
            is SpectatorContract.Event.Navigation.Back -> { setEffect { SpectatorContract.Effect.Navigation.Back } }
            is SpectatorContract.Event.OnLoad -> getSpectator()
        }
    }

    private fun getSpectator() {
        viewModelScope.launch {
            requestSpectatorUseCase(summonerName)
                .onStart { setState { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState { copy(data = it, isLoading = false) }
                    }.onFailure {
                        setState { copy(isLoading = false, error = it.message) }
                    }

                }
        }

    }

}