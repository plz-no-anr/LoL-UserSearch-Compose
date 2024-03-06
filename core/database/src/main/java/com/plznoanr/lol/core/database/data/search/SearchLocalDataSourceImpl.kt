package com.plznoanr.lol.core.database.data.search

import com.plznoanr.lol.core.database.dao.LolDao
import com.plznoanr.lol.core.database.model.SearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchLocalDataSourceImpl @Inject constructor(
    private val dao: LolDao
) : SearchLocalDataSource {

    override fun getSearch(): Flow<List<SearchEntity>?> = dao.getSearchList()

    override suspend fun upsertSearch(searchEntity: SearchEntity) {
        dao.upsertSearch(searchEntity)
    }

    override suspend fun deleteSearch(sName: String) {
        dao.deleteSearch(sName)
    }

    override suspend fun deleteSearchAll() {
        dao.deleteSearchAll()
    }

}