package com.plz.no.anr.lol.data.api

import com.plz.no.anr.lol.data.model.remote.LeagueResponse
import com.plz.no.anr.lol.data.model.remote.SpectatorResponse
import com.plz.no.anr.lol.data.model.remote.SummonerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface UserSearchApi {
    @GET(Endpoints.GET_SUMMONER)
    suspend fun getSummoner(
        @Path("summonerName") summonerName: String,
        @Query("api_key") apiKey: String
    ): Response<SummonerResponse?>

    @GET(Endpoints.GET_LEAGUE)
    suspend fun getLeague(
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
        @Query("api_key") apiKey: String
    ): Response<Set<LeagueResponse>?>

    @GET(Endpoints.GET_SPECTATOR)
    suspend fun getSpectator(
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
        @Query("api_key") apiKey: String
    ): Response<SpectatorResponse?>
}