package com.plz.no.anr.lol_usersearch_compose.ui.navigation

sealed class Route(val path: String) {

    object Main : Route("main")

    object Search : Route("search")

    object Summoner : Route("summoner") {
        const val KEY_SUMMONER_NAME = "summoner_name"
        val argsPath = "$path?{$KEY_SUMMONER_NAME}"
        fun pathWithArgs(summonerName: String) = "$path?$summonerName"
    }

    object Spectator : Route("spectator") {
        const val KEY_SUMMONER_NAME = "summoner_name"
        val argsPath = "$path?{$KEY_SUMMONER_NAME}"
        fun pathWithArgs(summonerName: String) = "$path?$summonerName"
    }

}
