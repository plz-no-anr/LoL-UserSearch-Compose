package com.plz.no.anr.lol.ui.feature.summoner

import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.ui.base.BaseContract

class SummonerContract : BaseContract() {

    data class State(
        val data: Summoner?,
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

        sealed class Spectator : Intent() {
            data class OnWatch(val name: String) : Spectator()
        }

    }

    sealed class SideEffect : BaseContract.SideEffect {

        data class Toast(val msg: String) : SideEffect()

        sealed class Navigation : SideEffect() {
            object Back : Navigation()

            data class ToSpectator(val name: String) : Navigation()
        }

    }

}