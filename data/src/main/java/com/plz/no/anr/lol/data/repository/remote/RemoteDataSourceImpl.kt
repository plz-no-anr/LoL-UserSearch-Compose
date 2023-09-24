package com.plz.no.anr.lol.data.repository.remote

import com.plz.no.anr.lol.data.api.UserSearchApi
import com.plz.no.anr.lol.data.model.remote.LeagueResponse
import com.plz.no.anr.lol.data.model.remote.SpectatorResponse
import com.plz.no.anr.lol.data.model.remote.SummonerResponse
import retrofit2.Response

internal class RemoteDataSourceImpl (
    private val api: UserSearchApi
): RemoteDataSource {
    override suspend fun requestSummoner(header: HashMap<String, String>, name: String): Result<SummonerResponse> {
        return api.getSummoner(header, name).asResult()
    }

    override suspend fun requestLeague(header: HashMap<String, String>, summonerId: String?): Result<Set<LeagueResponse>> {
        return api.getLeague(header, summonerId).asResult()
    }

    override suspend fun requestSpectator(header: HashMap<String, String>, summonerId: String?): Result<SpectatorResponse?> {
        return api.getSpectator(header, summonerId).asResultOrNull()
    }
}

private fun <T> Response<T?>.asResult(): Result<T> {
     return if (isSuccessful && body() != null) {
        Result.success(body()!!)
    } else {
        Result.failure(
            Exception("${code()}/${message()}")
        )
    }
}

private fun <T> Response<T?>.asResultOrNull(): Result<T?> {
    return if (isSuccessful) {
        Result.success(body())
    } else {
        Result.success(null)
    }
}