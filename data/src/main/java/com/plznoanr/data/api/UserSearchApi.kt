package com.plznoanr.data.api

import com.plznoanr.data.model.remote.LeagueResponse
import com.plznoanr.data.model.remote.SpectatorResponse
import com.plznoanr.data.model.remote.SummonerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserSearchApi {
    @GET(Endpoints.GET_SUMMONER)
    suspend fun getSummoner(
        @Path("summonerName") summonerName: String,
        @Query("api_key") api_key: String
    ): Response<SummonerResponse?>

    @GET(Endpoints.GET_LEAGUE)
    suspend fun getLeague(
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
        @Query("api_key") api_key: String
    ): Response<Set<LeagueResponse>?>

    @GET(Endpoints.GET_SPECTATOR)
    suspend fun getSpectator(
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
        @Query("api_key") api_key: String
    ): Response<SpectatorResponse?>
}