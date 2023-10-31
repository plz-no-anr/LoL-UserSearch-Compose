package com.plznoanr.data.repository

import com.plznoanr.data.repository.local.search.SearchLocalDataSource
import com.plznoanr.data.utils.asEntity
import com.plznoanr.data.utils.asSearchList
import com.plznoanr.model.Search
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