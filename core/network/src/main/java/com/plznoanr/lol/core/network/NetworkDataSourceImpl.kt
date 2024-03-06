package com.plznoanr.lol.core.network

import com.plznoanr.lol.core.network.model.AccountResponse
import com.plznoanr.lol.core.network.model.LeagueResponse
import com.plznoanr.lol.core.network.model.SpectatorResponse
import com.plznoanr.lol.core.network.model.SummonerResponse
import com.plznoanr.lol.core.network.retrofit.UserSearchApi
import com.plznoanr.lol.core.network.retrofit.asResult
import com.plznoanr.lol.core.network.retrofit.asResultOrNull
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val api: UserSearchApi
): NetworkDataSource {
    override suspend fun requestAccount(
        header: HashMap<String, String>,
        name: String,
        tag: String
    ): Result<AccountResponse> {
        return api.getAccount(header, name, tag).asResult()
    }

    override suspend fun requestSummoner(header: HashMap<String, String>, puuid: String): Result<SummonerResponse> {
        return api.getSummoner(header, puuid).asResult()
    }

    override suspend fun requestLeague(header: HashMap<String, String>, summonerId: String?): Result<Set<LeagueResponse>> {
        return api.getLeague(header, summonerId).asResult()
    }

    override suspend fun requestSpectator(header: HashMap<String, String>, summonerId: String?): Result<SpectatorResponse?> {
        return api.getSpectator(header, summonerId).asResultOrNull()
    }
}