package com.plznoanr.lol.feature.summoner

import com.plznoanr.lol.core.model.Summoner
import plznoanr.coma.core.ComaContract


data class SummonerUiState(
    val data: Summoner? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) : ComaContract.State

sealed class SummonerIntent : ComaContract.Intent {

    data object OnLoad : SummonerIntent()

    sealed class Navigation : SummonerIntent() {
        data object Back : Navigation()
    }

    sealed class Spectator : SummonerIntent() {
        data class OnWatch(val summonerId: String) : Spectator()
    }

}

sealed class SummonerSideEffect : ComaContract.SideEffect {

    data class Toast(val msg: String) : SummonerSideEffect()

    sealed class Navigation : SummonerSideEffect() {
        data object Back : Navigation()

        data class ToSpectator(val summonerId: String) : Navigation()
    }

}

