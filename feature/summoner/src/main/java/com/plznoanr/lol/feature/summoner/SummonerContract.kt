package com.plznoanr.lol.feature.summoner

import com.plznoanr.lol.core.model.Summoner


data class SummonerUiState(
    val data: Summoner? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface SummonerIntent {

    data object OnLoad : SummonerIntent

    sealed interface Navigation : SummonerIntent {
        data object Back : Navigation
    }

    sealed interface Spectator : SummonerIntent {
        data class OnWatch(val summonerId: String) : Spectator
    }

}

sealed interface SummonerSideEffect {

    data class Toast(val msg: String) : SummonerSideEffect

    sealed interface Navigation : SummonerSideEffect {
        data object Back : Navigation

        data class ToSpectator(val summonerId: String) : Navigation
    }

}

