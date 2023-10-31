package com.plznoanr.data.repository.remote

import com.plznoanr.data.model.remote.LeagueResponse
import com.plznoanr.data.model.remote.SpectatorResponse
import com.plznoanr.data.model.remote.SummonerResponse

interface RemoteDataSource {
    suspend fun requestSummoner(header: HashMap<String, String>, name: String): Result<SummonerResponse>

    suspend fun requestLeague(header: HashMap<String, String>, summonerId:String?): Result<Set<LeagueResponse>>

    suspend fun requestSpectator(header: HashMap<String, String>, summonerId:String?): Result<SpectatorResponse?>
}