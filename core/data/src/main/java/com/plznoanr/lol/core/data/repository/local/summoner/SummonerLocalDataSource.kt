package com.plznoanr.lol.core.data.repository.local.summoner

import com.plznoanr.lol.core.database.model.SummonerEntity
import kotlinx.coroutines.flow.Flow

interface SummonerLocalDataSource {

    fun getSummonerList(): Flow<List<SummonerEntity>?>

    fun getSummoner(name: String): Flow<SummonerEntity?>

    suspend fun insertSummoner(summonerEntity: SummonerEntity)

    suspend fun updateSummoner(summonerEntity: SummonerEntity)

    suspend fun deleteSummoner(name: String)

    suspend fun deleteSummonerAll()

}