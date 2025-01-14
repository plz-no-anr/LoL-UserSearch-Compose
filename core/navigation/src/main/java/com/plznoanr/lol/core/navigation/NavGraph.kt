package com.plznoanr.lol.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavGraph {

    @Serializable
    data object HomeRoute: NavGraph

    @Serializable
    data object SearchGraph: NavGraph {
        @Serializable
        data object SearchRoute: NavGraph
    }

    @Serializable
    data object BookmarkRoute: NavGraph

    @Serializable
    data object SettingRoute: NavGraph

    @Serializable
    data class SummonerRoute(val summonerName: String, val summonerTag: String): NavGraph

    @Serializable
    data object SpectatorRoute: NavGraph

}