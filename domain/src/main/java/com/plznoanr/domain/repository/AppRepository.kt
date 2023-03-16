package com.plznoanr.domain.repository

import com.plznoanr.domain.model.Search
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    var apiKey: String
    fun getSearchList(): Flow<Result<List<Search>>>
    fun insertSearch(search: Search): Flow<Result<Unit>>
    fun deleteSearch(uid: Int): Flow<Result<Unit>>
    fun deleteSearchAll(): Flow<Result<Unit>>
}