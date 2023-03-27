package com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator

import androidx.lifecycle.SavedStateHandle
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpectatorViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
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
            is SpectatorContract.Event.OnLoad -> { }
        }
    }

}