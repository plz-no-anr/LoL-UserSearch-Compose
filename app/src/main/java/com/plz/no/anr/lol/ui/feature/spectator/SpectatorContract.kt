package com.plz.no.anr.lol.ui.feature.spectator

import com.plz.no.anr.lol.domain.model.Spectator
import com.plz.no.anr.lol.ui.base.BaseContract

class SpectatorContract : BaseContract() {

    data class State(
        val data: Spectator? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : BaseContract.State

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