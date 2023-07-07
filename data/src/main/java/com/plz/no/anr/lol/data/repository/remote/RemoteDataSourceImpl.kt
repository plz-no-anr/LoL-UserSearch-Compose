package com.plz.no.anr.lol.data.repository.remote

import com.plz.no.anr.lol.data.api.UserSearchApi
import com.plz.no.anr.lol.data.model.remote.LeagueResponse
import com.plz.no.anr.lol.data.model.remote.SpectatorResponse
import com.plz.no.anr.lol.data.model.remote.SummonerResponse
import retrofit2.Response

internal class RemoteDataSourceImpl (
    private val api: UserSearchApi
): RemoteDataSource {
    override suspend fun requestSummoner(name: String, apiKey: String): SummonerResponse {
        return api.getSummoner(name, apiKey).asResult()
    }

    override suspend fun requestLeague(summonerId: String?, apiKey: String): Set<LeagueResponse> {
        return api.getLeague(summonerId, apiKey).asResult()
    }

    override suspend fun requestSpectator(summonerId: String?, apiKey: String): SpectatorResponse? {
        return api.getSpectator(summonerId, apiKey).run {
            if (isSuccessful) {
                body()
            } else {
                null
            }
        }
    }
}

private fun <T> Response<T?>.asResult(): T {
    if (isSuccessful && body() != null) {
        return body()!!
    } else {
        throw Exception("${code()}/${message()}")
    }
}