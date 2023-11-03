package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.database.data.search.SearchLocalDataSource
import com.plznoanr.lol.core.data.utils.asEntity
import com.plznoanr.lol.core.data.utils.asSearchList
import com.plznoanr.lol.core.model.Search
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val localDataSource: SearchLocalDataSource,
) : SearchRepository {

    override fun getSearchList(): Flow<Result<List<Search>>> =
        localDataSource.getSearch().map {
            Result.success(
                it?.asSearchList() ?: emptyList()
            )
        }

    override suspend fun upsertSearch(search: Search) {
        localDataSource.upsertSearch(search.asEntity())
    }

    override fun insertSearch(search: Search): Flow<Result<Unit>> = flow {
        localDataSource.insertSearch(search.asEntity()).run {
            emit(Result.success(Unit))
        }
    }

    override fun updateSearch(search: Search): Flow<Result<Unit>> = flow {
        localDataSource.updateSearch(search.asEntity()).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteSearch(sName: String): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearch(sName).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteSearchAll(): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearchAll().run {
            emit(Result.success(Unit))
        }
    }

}