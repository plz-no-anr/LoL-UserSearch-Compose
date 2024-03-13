package com.plznoanr.lol.core.network

import com.plznoanr.lol.core.common.model.Result
import com.plznoanr.lol.core.network.model.AccountResponse
import com.plznoanr.lol.core.network.model.LeagueResponse
import com.plznoanr.lol.core.network.model.SpectatorResponse
import com.plznoanr.lol.core.network.model.SummonerResponse
import com.plznoanr.lol.core.network.retrofit.RiotAccountApi
import com.plznoanr.lol.core.network.retrofit.UserSearchApi
import com.plznoanr.lol.core.network.retrofit.asResult
import com.plznoanr.lol.core.network.retrofit.asResultOrNull
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val accountApi: RiotAccountApi,
    private val userSearchApi: UserSearchApi
): NetworkDataSource {
    override suspend fun requestAccount(
        header: HashMap<String, String>,
        name: String,
        tag: String
    ): Result<AccountResponse> {
        return accountApi.getAccount(header, name, tag).asResult()
    }

    override suspend fun requestSummoner(header: HashMap<String, String>, puuid: String): Result<SummonerResponse> {
        return userSearchApi.getSummoner(header, puuid).asResult()
    }

    override suspend fun requestLeague(header: HashMap<String, String>, summonerId: String?): Result<Set<LeagueResponse>> {
        return userSearchApi.getLeague(header, summonerId).asResult()
    }

    override suspend fun requestSpectator(header: HashMap<String, String>, summonerId: String?): Result<SpectatorResponse?> {
        return userSearchApi.getSpectator(header, summonerId).asResultOrNull()
    }
}