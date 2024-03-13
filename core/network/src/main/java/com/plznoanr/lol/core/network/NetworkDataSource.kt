package com.plznoanr.lol.core.network

import com.plznoanr.lol.core.common.model.Result
import com.plznoanr.lol.core.network.model.AccountResponse
import com.plznoanr.lol.core.network.model.LeagueResponse
import com.plznoanr.lol.core.network.model.SpectatorResponse
import com.plznoanr.lol.core.network.model.SummonerResponse

interface NetworkDataSource {
    suspend fun requestAccount(header: HashMap<String, String>, name: String, tag: String): Result<AccountResponse>

    suspend fun requestSummoner(header: HashMap<String, String>, puuid: String): Result<SummonerResponse>

    suspend fun requestLeague(header: HashMap<String, String>, summonerId:String?): Result<Set<LeagueResponse>>

    suspend fun requestSpectator(header: HashMap<String, String>, summonerId:String?): Result<SpectatorResponse?>
}