package com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator

import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plznoanr.domain.model.Spectator

class SpectatorContract : BaseContract() {

    data class UiState(
        val data: Spectator?,
        val isLoading: Boolean = false,
        val error: String? = null
    ): ViewState

    sealed class Event : ViewEvent {
        object OnLoad : Event()
        object Navigation {
            object Back: Event()
        }
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation: Effect() {
            object Back: Navigation()
        }

    }
}

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