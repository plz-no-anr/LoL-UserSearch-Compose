package com.plznoanr.lol.core.network

import com.plznoanr.lol.core.network.model.LeagueResponse
import com.plznoanr.lol.core.network.model.SpectatorResponse
import com.plznoanr.lol.core.network.model.SummonerResponse
import com.plznoanr.lol.core.network.retrofit.UserSearchApi
import com.plznoanr.lol.core.network.retrofit.asResult
import com.plznoanr.lol.core.network.retrofit.asResultOrNull
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun requestSummoner(header: HashMap<String, String>, name: String): Result<SummonerResponse>

    suspend fun requestLeague(header: HashMap<String, String>, summonerId:String?): Result<Set<LeagueResponse>>

    suspend fun requestSpectator(header: HashMap<String, String>, summonerId:String?): Result<SpectatorResponse?>
}