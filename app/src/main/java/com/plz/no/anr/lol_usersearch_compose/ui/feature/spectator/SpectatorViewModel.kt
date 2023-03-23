package com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator

import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel

class SpectatorViewModel(

): BaseViewModel<SpectatorContract.UiState, SpectatorContract.Event, SpectatorContract.Effect>() {
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