package com.plznoanr.data.api

import com.plznoanr.data.model.remote.LeagueResponse
import com.plznoanr.data.model.remote.SpectatorResponse
import com.plznoanr.data.model.remote.SummonerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

interface UserSearchApi {
    @GET("/summoner/v4/summoners/by-name/{summonerName}")
    suspend fun getSummoner(
        @HeaderMap header: Map<String, String>,
        @Path("summonerName") summonerName: String,
    ): Response<SummonerResponse?>

    @GET("/league/v4/entries/by-summoner/{encryptedSummonerId}")
    suspend fun getLeague(
        @HeaderMap header: Map<String, String>,
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
    ): Response<Set<LeagueResponse>?>

    @GET("/spectator/v4/active-games/by-summoner/{encryptedSummonerId}")
    suspend fun getSpectator(
        @HeaderMap header: Map<String, String>,
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
    ): Response<SpectatorResponse?>
}