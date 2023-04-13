package com.plznoanr.lol_usersearch_compose.ui.feature.spectator

import com.plznoanr.domain.model.Spectator
import com.plznoanr.lol_usersearch_compose.ui.base.BaseContract

class SpectatorContract : BaseContract() {

    data class State(
        val data: Spectator?,
        val isLoading: Boolean,
        val error: String?
    ) : BaseContract.State {

            companion object {
                fun initial() = State(
                    data = null,
                    isLoading = false,
                    error = null
                )
            }
    }

    sealed class Intent : BaseContract.Intent {

        object OnLoad : Intent()

        sealed class Navigation : Intent() {
            object Back : Navigation()
        }
    }

    sealed class SideEffect : BaseContract.SideEffect {
        data class Toast(val msg: String) : SideEffect()

        sealed class Navigation : SideEffect() {
            object Back : Navigation()
        }

    }

}