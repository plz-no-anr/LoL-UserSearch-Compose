package com.plznoanr.lol.core.database.data.summoner

import com.plznoanr.lol.core.database.dao.LolDao
import com.plznoanr.lol.core.database.model.SummonerEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultSummonerLocalDataSource @Inject constructor(
    private val dao: LolDao,
) : SummonerLocalDataSource {

    override fun getSummonerAll(): Flow<List<SummonerEntity>?> = dao.getSummonerList()

    override fun getSummonerList(page: Int, size: Int): Flow<List<SummonerEntity>?> = dao.getSummonerList(
        page = page,
        size = size
    )

    override fun getSummoner(summonerId: String): Flow<SummonerEntity?> = dao.getSummoner(summonerId)

    override suspend fun upsertSummoner(summonerEntity: SummonerEntity) {
        dao.upsertSummoner(summonerEntity)
    }

    override suspend fun deleteSummoner(name: String) {
        dao.deleteSummoner(name)
    }

    override suspend fun deleteSummonerAll() {
        dao.deleteSummonerAll()
    }

}