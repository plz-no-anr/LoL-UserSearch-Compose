package com.plznoanr.data.repository.remote

import com.plznoanr.data.api.UserSearchApi
import com.plznoanr.data.model.remote.LeagueResponse
import com.plznoanr.data.model.remote.SpectatorResponse
import com.plznoanr.data.model.remote.SummonerResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun requestSummoner(name: String, apiKey: String): SummonerResponse

    suspend fun requestLeague(summonerId:String?, apiKey: String): Set<LeagueResponse>

    suspend fun requestSpectator(summonerId:String?, apiKey: String): SpectatorResponse?
}

class RemoteDataSourceImpl (
    private val api: UserSearchApi
): RemoteDataSource {
    override suspend fun requestSummoner(name: String, apiKey: String): SummonerResponse {
        return api.getSummoner(name, apiKey).asResult()
    }

    override suspend fun requestLeague(summonerId: String?, apiKey: String): Set<LeagueResponse> {
        return api.getLeague(summonerId, apiKey).asResult()
    }

    override suspend fun requestSpectator(summonerId: String?, apiKey: String): SpectatorResponse? {
        return api.getSpectator(summonerId, apiKey).let {
            if (it.isSuccessful) {
                it.body()
            } else {
                null
            }
        }
    }
}

fun <T> Response<T?>.asResult(): T {
    if (isSuccessful && body() != null) {
        return body()!!
    } else {
        throw Exception("${code()}/${message()}")
    }
}