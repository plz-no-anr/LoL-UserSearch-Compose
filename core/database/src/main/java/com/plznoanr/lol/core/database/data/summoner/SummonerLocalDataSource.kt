package com.plznoanr.lol.core.database.data.summoner

import com.plznoanr.lol.core.database.model.SummonerEntity
import kotlinx.coroutines.flow.Flow

interface SummonerLocalDataSource {

    fun getSummonerAll(): Flow<List<SummonerEntity>?>

    fun getSummonerList(page: Int, size: Int): Flow<List<SummonerEntity>?>

    fun getSummoner(name: String): Flow<SummonerEntity?>

    suspend fun upsertSummoner(summonerEntity: SummonerEntity)

    suspend fun deleteSummoner(name: String)

    suspend fun deleteSummonerAll()

}