package com.plz.no.anr.lol.ui.feature.spectator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol.domain.usecase.spectator.RequestSpectatorUseCase
import com.plz.no.anr.lol.ui.feature.spectator.SpectatorContract.Intent
import com.plz.no.anr.lol.ui.feature.spectator.SpectatorContract.SideEffect
import com.plz.no.anr.lol.ui.feature.spectator.SpectatorContract.State
import com.plz.no.anr.lol.ui.navigation.Destination
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
) : ComaViewModel<State, Intent, SideEffect>() {

    private val summonerName: String? by lazy {
        stateHandle.get<String>(Destination.Spectator.Args.KEY_SUMMONER_NAME)
    }

    override fun setInitialState(): State = State()

    override fun handleIntents(intent: Intent) {
        when (intent) {
            is Intent.Navigation.Back -> postSideEffect { SideEffect.Navigation.Back }
            is Intent.OnLoad -> getSpectator()
        }
    }

    private fun getSpectator() {
        summonerName?.let {
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