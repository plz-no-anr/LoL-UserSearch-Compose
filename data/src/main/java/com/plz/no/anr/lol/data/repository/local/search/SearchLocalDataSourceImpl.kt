package com.plz.no.anr.lol.data.repository.local.search

import com.plz.no.anr.lol.data.db.dao.LolDao
import com.plz.no.anr.lol.data.model.local.SearchEntity

class SearchLocalDataSourceImpl(
    private val dao: LolDao
) : SearchLocalDataSource {

    override suspend fun getSearch(): List<SearchEntity> = dao.getSearch()

    override suspend fun insertSearch(searchEntity: SearchEntity) {
        dao.insertSearch(searchEntity)
    }

    override suspend fun updateSearch(searchEntity: SearchEntity) {
        dao.updateSearch(searchEntity)
    }

    override suspend fun deleteSearch(sName: String) {
        dao.deleteSearch(sName)
    }

    override suspend fun deleteSearchAll() {
        dao.deleteSearchAll()
    }

}