package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.data.utils.asEntity
import com.plznoanr.lol.core.database.data.search.SearchLocalDataSource
import com.plznoanr.lol.core.database.model.SearchEntity
import com.plznoanr.lol.core.database.model.asDomain
import com.plznoanr.lol.core.model.Search
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val localDataSource: SearchLocalDataSource,
    @AppDispatchers.IO private val ioDispatcher: CoroutineDispatcher
) : SearchRepository {

    override fun getSearchList(): Flow<List<Search>> =
        localDataSource.getSearch().map {
            it?.map(SearchEntity::asDomain) ?: emptyList()
        }

    override suspend fun upsertSearch(search: Search) {
        withContext(ioDispatcher) {
            localDataSource.upsertSearch(search.asEntity())
        }
    }

    override suspend fun deleteSearch(sName: String) {
        withContext(ioDispatcher) {
            localDataSource.deleteSearch(sName)
        }
    }

    override suspend fun deleteSearchAll() {
        withContext(ioDispatcher) {
            localDataSource.deleteSearchAll()
        }
    }

}