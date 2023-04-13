package com.plznoanr.lol_usersearch_compose.ui.feature.spectator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plznoanr.domain.usecase.spectator.RequestSpectatorUseCase
import com.plznoanr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plznoanr.lol_usersearch_compose.ui.feature.spectator.SpectatorContract.*
import com.plznoanr.lol_usersearch_compose.ui.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpectatorViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSpectatorUseCase: RequestSpectatorUseCase
) : BaseViewModel<State, Intent, SideEffect>() {

    private val summonerName: String? by lazy {
        stateHandle.get<String>(Route.Spectator.KEY_SUMMONER_NAME)
    }

    override fun setInitialState(): State = State.initial()

    override fun handleIntents(intent: Intent) {
        when (intent) {
            is Intent.Navigation.Back -> postSideEffect { SideEffect.Navigation.Back }
            is Intent.OnLoad -> getSpectator()
        }
    }

    private fun getSpectator() {
        viewModelScope.launch {
            summonerName?.let {
                requestSpectatorUseCase(it.trim())
                    .onStart { reduce { copy(isLoading = true) } }
                    .collect { result ->
                        result.onSuccess {
                            reduce { copy(data = it, isLoading = false) }
                        }.onFailure {
                            reduce { copy(error = it.message, isLoading = false) }
                        }
                    }
            } ?: run {
                reduce { copy(error = "Summoner name is null", isLoading = false) }
            }
        }
    }

}