package com.plz.no.anr.lol.ui.feature.summoner

import com.plz.no.anr.lol.domain.model.Summoner
import plznoanr.coma.core.ComaContract

class SummonerContract : ComaContract() {

    data class State(
        val data: Summoner? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ComaContract.State

    sealed class Intent : ComaContract.Intent {

        object OnLoad : Intent()

        sealed class Navigation : Intent() {
            object Back : Navigation()
        }

        sealed class Spectator : Intent() {
            data class OnWatch(val name: String) : Spectator()
        }

    }

    sealed class SideEffect : ComaContract.SideEffect {

        data class Toast(val msg: String) : SideEffect()

        sealed class Navigation : SideEffect() {
            object Back : Navigation()

            data class ToSpectator(val name: String) : Navigation()
        }

    }

}