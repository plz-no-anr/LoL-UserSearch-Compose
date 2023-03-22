package com.plznoanr.data.repository.local

import com.plznoanr.data.db.AppDao
import com.plznoanr.data.model.local.ProfileEntity
import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.data.model.local.SummonerEntity

interface LocalDataSource {
    suspend fun getSearch() : List<SearchEntity>

    suspend fun insertSearch(searchEntity: SearchEntity)

    suspend fun deleteSearch(sName: String)

    suspend fun deleteSearchAll()

    suspend fun getProfile() : ProfileEntity

    suspend fun insertProfile(profileEntity: ProfileEntity)

    suspend fun deleteProfile()

    suspend fun getSummoner() : List<SummonerEntity>

    suspend fun insertSummoner(summonerEntity: SummonerEntity)

    suspend fun deleteSummoner(name: String)

    suspend fun deleteSummonerAll()

}

class LocalDataSourceImpl (
    private val dao: AppDao
) : LocalDataSource {
    override suspend fun getSearch(): List<SearchEntity> = dao.getSearch()
    override suspend fun insertSearch(searchEntity: SearchEntity) {
        dao.insertSearch(searchEntity)
    }

    override suspend fun deleteSearch(sName: String) {
        dao.deleteSearch(sName)
    }

    override suspend fun deleteSearchAll() {
        dao.deleteSearchAll()
    }

    override suspend fun getProfile(): ProfileEntity = dao.getProfile()

    override suspend fun insertProfile(profileEntity: ProfileEntity) {
        dao.insertProfile(profileEntity)
    }

    override suspend fun deleteProfile() {
        dao.deleteProfile()
    }

    override suspend fun getSummoner(): List<SummonerEntity> = dao.getSummoner()

    override suspend fun insertSummoner(summonerEntity: SummonerEntity) {
        dao.insertSummoner(summonerEntity)
    }

    override suspend fun deleteSummoner(name: String) {
        dao.deleteSummoner(name)
    }

    override suspend fun deleteSummonerAll() {
        dao.deleteSummonerAll()
    }

}