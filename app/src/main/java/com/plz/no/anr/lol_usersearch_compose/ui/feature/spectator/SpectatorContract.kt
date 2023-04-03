package com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator

import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plznoanr.domain.model.Spectator

class SpectatorContract : BaseContract() {

    data class UiState(
        val data: Spectator?,
        val isLoading: Boolean,
        val error: String?
    ) : ViewState {

            companion object {
                fun initial() = UiState(
                    data = null,
                    isLoading = false,
                    error = null
                )
            }
    }

    sealed class Event : ViewEvent {

        object OnLoad : Event()

        sealed class Navigation : Event() {
            object Back : Navigation()
        }
    }

    sealed class Effect : ViewSideEffect {
        data class Toast(val msg: String) : Effect()

        sealed class Navigation : Effect() {
            object Back : Navigation()
        }

    }

}