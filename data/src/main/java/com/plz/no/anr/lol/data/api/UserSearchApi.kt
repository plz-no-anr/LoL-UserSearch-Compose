package com.plz.no.anr.lol.data.api

import com.plz.no.anr.lol.data.model.remote.LeagueResponse
import com.plz.no.anr.lol.data.model.remote.SpectatorResponse
import com.plz.no.anr.lol.data.model.remote.SummonerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

internal interface UserSearchApi {
    @GET(Endpoints.GET_SUMMONER)
    suspend fun getSummoner(
        @HeaderMap header: Map<String, String>,
        @Path("summonerName") summonerName: String,
    ): Response<SummonerResponse?>

    @GET(Endpoints.GET_LEAGUE)
    suspend fun getLeague(
        @HeaderMap header: Map<String, String>,
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
    ): Response<Set<LeagueResponse>?>

    @GET(Endpoints.GET_SPECTATOR)
    suspend fun getSpectator(
        @HeaderMap header: Map<String, String>,
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
    ): Response<SpectatorResponse?>
}