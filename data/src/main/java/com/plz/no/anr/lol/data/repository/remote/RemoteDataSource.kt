package com.plz.no.anr.lol.data.repository.remote

import com.plz.no.anr.lol.data.model.remote.LeagueResponse
import com.plz.no.anr.lol.data.model.remote.SpectatorResponse
import com.plz.no.anr.lol.data.model.remote.SummonerResponse

internal interface RemoteDataSource {
    suspend fun requestSummoner(name: String, apiKey: String): SummonerResponse

    suspend fun requestLeague(summonerId:String?, apiKey: String): Set<LeagueResponse>

    suspend fun requestSpectator(summonerId:String?, apiKey: String): SpectatorResponse?
}