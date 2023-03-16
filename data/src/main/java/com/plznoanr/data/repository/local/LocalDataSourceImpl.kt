package com.plznoanr.data.repository.local

import com.plznoanr.data.db.AppDao
import com.plznoanr.data.model.local.SearchEntity

interface LocalDataSource {
    suspend fun getSearch() : List<SearchEntity>

    suspend fun insertSearch(searchEntity: SearchEntity)

    suspend fun deleteSearch(uid: Int)

    suspend fun deleteSearchAll()
}

class LocalDataSourceImpl (
    private val dao: AppDao
) : LocalDataSource {
    override suspend fun getSearch(): List<SearchEntity> = dao.getSearch()
    override suspend fun insertSearch(searchEntity: SearchEntity) {
        dao.insertSearch(searchEntity)
    }

    override suspend fun deleteSearch(uid: Int) {
        dao.deleteSearch(uid)
    }

    override suspend fun deleteSearchAll() {
        dao.deleteSearchAll()
    }

}