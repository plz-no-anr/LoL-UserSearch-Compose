package com.plz.no.anr.lol.data.repository.remote

import com.plz.no.anr.lol.data.model.remote.LeagueResponse
import com.plz.no.anr.lol.data.model.remote.SpectatorResponse
import com.plz.no.anr.lol.data.model.remote.SummonerResponse

internal interface RemoteDataSource {
    suspend fun requestSummoner(header: HashMap<String, String>, name: String): SummonerResponse

    suspend fun requestLeague(header: HashMap<String, String>, summonerId:String?): Set<LeagueResponse>

    suspend fun requestSpectator(header: HashMap<String, String>, summonerId:String?): SpectatorResponse?
}