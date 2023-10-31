package com.plznoanr.data.repository.local.search

import com.plznoanr.data.db.dao.LolDao
import com.plznoanr.data.model.local.SearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchLocalDataSourceImpl @Inject constructor(
    private val dao: LolDao
) : SearchLocalDataSource {

    override fun getSearch(): Flow<List<SearchEntity>?> = dao.getSearchList()

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