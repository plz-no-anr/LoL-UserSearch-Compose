package com.plznoanr.data.repository.local.summoner

import com.plznoanr.data.db.dao.LolDao
import com.plznoanr.data.model.local.SummonerEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SummonerLocalDataSourceImpl @Inject constructor(
    private val dao: LolDao
) : SummonerLocalDataSource {

    override fun getSummonerList(): Flow<List<SummonerEntity>?> = dao.getSummonerList()

    override fun getSummoner(name: String): Flow<SummonerEntity?> = dao.getSummoner(name)

    override suspend fun insertSummoner(summonerEntity: SummonerEntity) {
        dao.insertSummoner(summonerEntity)
    }

    override suspend fun updateSummoner(summonerEntity: SummonerEntity) {
        dao.updateSummoner(summonerEntity)
    }

    override suspend fun deleteSummoner(name: String) {
        dao.deleteSummoner(name)
    }

    override suspend fun deleteSummonerAll() {
        dao.deleteSummonerAll()
    }

}