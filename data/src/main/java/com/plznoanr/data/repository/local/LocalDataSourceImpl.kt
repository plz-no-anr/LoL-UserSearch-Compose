package com.plznoanr.data.repository.local

import com.plznoanr.data.db.dao.AppDao
import com.plznoanr.data.model.local.ProfileEntity
import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.data.model.local.SummonerEntity

interface LocalDataSource {
    suspend fun getSearch() : List<SearchEntity>

    suspend fun insertSearch(searchEntity: SearchEntity)

    suspend fun deleteSearch(sName: String)

    suspend fun deleteSearchAll()

    suspend fun getProfile() : ProfileEntity?

    suspend fun insertProfile(profileEntity: ProfileEntity)

    suspend fun deleteProfile()

    suspend fun getSummoner() : List<SummonerEntity>

    suspend fun insertSummoner(summonerEntity: SummonerEntity)

    suspend fun deleteSummoner(name: String)

    suspend fun deleteSummonerAll()

}

class LocalDataSourceImpl (
    private val appDao: AppDao
) : LocalDataSource {
    override suspend fun getSearch(): List<SearchEntity> = appDao.getSearch()
    override suspend fun insertSearch(searchEntity: SearchEntity) {
        appDao.insertSearch(searchEntity)
    }

    override suspend fun deleteSearch(sName: String) {
        appDao.deleteSearch(sName)
    }

    override suspend fun deleteSearchAll() {
        appDao.deleteSearchAll()
    }

    override suspend fun getProfile(): ProfileEntity = appDao.getProfile()

    override suspend fun insertProfile(profileEntity: ProfileEntity) {
        appDao.insertProfile(profileEntity)
    }

    override suspend fun deleteProfile() {
        appDao.deleteProfile()
    }

    override suspend fun getSummoner(): List<SummonerEntity> = appDao.getSummoner()

    override suspend fun insertSummoner(summonerEntity: SummonerEntity) {
        appDao.insertSummoner(summonerEntity)
    }

    override suspend fun deleteSummoner(name: String) {
        appDao.deleteSummoner(name)
    }

    override suspend fun deleteSummonerAll() {
        appDao.deleteSummonerAll()
    }

}