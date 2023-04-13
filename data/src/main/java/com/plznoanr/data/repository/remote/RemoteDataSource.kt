package com.plznoanr.data.repository.remote

import com.plznoanr.data.model.remote.LeagueResponse
import com.plznoanr.data.model.remote.SpectatorResponse
import com.plznoanr.data.model.remote.SummonerResponse

internal interface RemoteDataSource {
    suspend fun requestSummoner(name: String, apiKey: String): SummonerResponse

    suspend fun requestLeague(summonerId:String?, apiKey: String): Set<LeagueResponse>

    suspend fun requestSpectator(summonerId:String?, apiKey: String): SpectatorResponse?
}