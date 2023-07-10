package com.plz.no.anr.lol.data.repository.local.summoner

import com.plz.no.anr.lol.data.model.local.SummonerEntity

interface SummonerLocalDataSource {

    suspend fun getSummoner() : List<SummonerEntity>

    suspend fun insertSummoner(summonerEntity: SummonerEntity)

    suspend fun updateSummoner(summonerEntity: SummonerEntity)

    suspend fun deleteSummoner(name: String)

    suspend fun deleteSummonerAll()

}