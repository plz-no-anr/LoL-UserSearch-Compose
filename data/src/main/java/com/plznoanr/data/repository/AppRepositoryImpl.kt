package com.plznoanr.data.repository

import com.plznoanr.data.di.CoroutineQualifiers
import com.plznoanr.data.repository.local.LocalDataSource
import com.plznoanr.data.repository.remote.RemoteDataSource
import com.plznoanr.data.repository.local.PreferenceDataSource
import com.plznoanr.data.utils.toEntity
import com.plznoanr.domain.model.Search
import com.plznoanr.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AppRepositoryImpl (
    @CoroutineQualifiers.IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val preferenceDataSource: PreferenceDataSource
) : AppRepository {
    override var apiKey: String
        get() = preferenceDataSource.apiKey
        set(value) {
            preferenceDataSource.apiKey = value
        }

    override fun getSearchList(): Flow<Result<List<Search>>> = flow {
        emit(makeResult(coroutineDispatcher) {
            localDataSource.getSearch().map { it.toDomain() }
        })
    }.flowOn(coroutineDispatcher)

    override fun insertSearch(search: Search): Flow<Result<Unit>> = flow {
        localDataSource.insertSearch(search.toEntity())
        emit(Result.success(Unit))
    }.flowOn(coroutineDispatcher)


    override fun deleteSearch(uid: Int): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearch(uid)
        emit(Result.success(Unit))
    }.flowOn(coroutineDispatcher)

    override fun deleteSearchAll(): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearchAll()
        emit(Result.success(Unit))
    }.flowOn(coroutineDispatcher)

}

suspend fun <T> makeResult(
    dispatcher: CoroutineDispatcher,
    call: suspend () -> T
): Result<T> = runCatching {
    withContext(dispatcher) {
        call()
    }
}