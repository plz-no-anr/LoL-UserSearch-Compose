package com.plz.no.anr.lol.data.repository.local.summoner

import com.plz.no.anr.lol.data.db.dao.LolDao
import com.plz.no.anr.lol.data.model.local.SummonerEntity
import kotlinx.coroutines.flow.Flow

class SummonerLocalDataSourceImpl(
    private val dao: LolDao
) : SummonerLocalDataSource {

    override fun getSummonerList(): Flow<List<SummonerEntity>?> = dao.getSummonerList()

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