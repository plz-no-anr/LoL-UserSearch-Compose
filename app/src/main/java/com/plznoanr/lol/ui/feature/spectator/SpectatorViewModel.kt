package com.plznoanr.lol.ui.feature.spectator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plznoanr.domain.usecase.spectator.RequestSpectatorUseCase
import com.plznoanr.lol.ui.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import plznoanr.coma.core.ComaViewModel
import javax.inject.Inject

@HiltViewModel
class SpectatorViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSpectatorUseCase: RequestSpectatorUseCase
) : ComaViewModel<SpectatorUiState, SpectatorIntent, SpectatorSideEffect>() {

    private val summonerId: String? by lazy {
        stateHandle.get<String>(Destination.Spectator.Args.KEY_SUMMONER_ID)
    }

    override fun setInitialState(): SpectatorUiState = SpectatorUiState()

    override fun handleIntents(intent: SpectatorIntent) {
        when (intent) {
            is SpectatorIntent.Navigation.Back -> postSideEffect { SpectatorSideEffect.Navigation.Back }
            is SpectatorIntent.OnLoad -> getSpectator()
        }
    }

    private fun getSpectator() {
        summonerId?.let {
            requestSpectatorUseCase(it.trim())
                .onStart { reduce { copy(isLoading = true) } }
                .onEach { result ->
                    result.onSuccess {
                        reduce { copy(data = it, isLoading = false) }
                    }.onFailure {
                        reduce { copy(error = it.message, isLoading = false) }
                    }
                }.launchIn(viewModelScope)
        } ?: run {
            reduce { copy(error = "Summoner name is null", isLoading = false) }
        }
    }

}