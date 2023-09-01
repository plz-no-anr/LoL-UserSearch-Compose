package com.plz.no.anr.lol.ui.feature.spectator

import com.plz.no.anr.lol.domain.model.Spectator
import plznoanr.coma.core.ComaContract

class SpectatorContract : ComaContract() {

    data class State(
        val data: Spectator? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ComaContract.State

    sealed class Intent : ComaContract.Intent {

        object OnLoad : Intent()

        sealed class Navigation : Intent() {
            object Back : Navigation()
        }
    }

    sealed class SideEffect : ComaContract.SideEffect {
        data class Toast(val msg: String) : SideEffect()

        sealed class Navigation : SideEffect() {
            object Back : Navigation()
        }

    }

}