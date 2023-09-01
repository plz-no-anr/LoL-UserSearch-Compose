package com.plz.no.anr.lol.data.repository.remote

import com.plz.no.anr.lol.data.api.UserSearchApi
import com.plz.no.anr.lol.data.model.remote.LeagueResponse
import com.plz.no.anr.lol.data.model.remote.SpectatorResponse
import com.plz.no.anr.lol.data.model.remote.SummonerResponse
import retrofit2.Response

internal class RemoteDataSourceImpl (
    private val api: UserSearchApi
): RemoteDataSource {
    override suspend fun requestSummoner(header: HashMap<String, String>, name: String): SummonerResponse {
        return api.getSummoner(header, name).asResult()
    }

    override suspend fun requestLeague(header: HashMap<String, String>, summonerId: String?): Set<LeagueResponse> {
        return api.getLeague(header, summonerId).asResult()
    }

    override suspend fun requestSpectator(header: HashMap<String, String>, summonerId: String?): SpectatorResponse? {
        return api.getSpectator(header, summonerId).run {
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