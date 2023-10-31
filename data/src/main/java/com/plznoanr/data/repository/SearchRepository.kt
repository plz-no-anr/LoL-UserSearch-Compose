package com.plznoanr.data.repository

import com.plznoanr.model.Search
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearchList(): Flow<Result<List<Search>>>

    fun insertSearch(search: Search): Flow<Result<Unit>>

    fun updateSearch(search: Search): Flow<Result<Unit>>

    fun deleteSearch(sName: String): Flow<Result<Unit>>

    fun deleteSearchAll(): Flow<Result<Unit>>

}