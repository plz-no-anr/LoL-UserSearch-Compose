package com.plznoanr.lol.core.network.retrofit

import com.plznoanr.lol.core.network.model.LeagueResponse
import com.plznoanr.lol.core.network.model.SpectatorResponse
import com.plznoanr.lol.core.network.model.SummonerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

interface UserSearchApi {

    @GET("/lol/summoner/v4/summoners/by-puuid/{encryptedPUUID}")
    suspend fun getSummoner(
        @HeaderMap header: Map<String, String>,
        @Path("encryptedPUUID") puuid: String,
    ): Response<SummonerResponse?>

    @GET("/lol/league/v4/entries/by-summoner/{encryptedSummonerId}")
    suspend fun getLeague(
        @HeaderMap header: Map<String, String>,
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
    ): Response<Set<LeagueResponse>?>

    @GET("/lol/spectator/v4/active-games/by-summoner/{encryptedSummonerId}")
    suspend fun getSpectator(
        @HeaderMap header: Map<String, String>,
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
    ): Response<SpectatorResponse?>
}

internal fun <T> Response<T?>.asResult(): Result<T> {
    return if (isSuccessful && body() != null) {
        Result.success(body()!!)
    } else {
        Result.failure(
            Exception("${code()}/${message()}")
        )
    }
}

internal fun <T> Response<T?>.asResultOrNull(): Result<T?> {
    return if (isSuccessful) {
        Result.success(body())
    } else {
        Result.success(null)
    }
}