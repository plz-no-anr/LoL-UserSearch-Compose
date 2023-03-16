package com.plznoanr.data.api

object Endpoints {
    const val BASE_URL = "https://kr.api.riotgames.com/lol/"

    const val GET_SUMMONER = "summoner/v4/summoners/by-name/{summonerName}"
    const val GET_LEAGUE = "league/v4/entries/by-summoner/{encryptedSummonerId}"
    const val GET_SPECTATOR = "spectator/v4/active-games/by-summoner/{encryptedSummonerId}"
}