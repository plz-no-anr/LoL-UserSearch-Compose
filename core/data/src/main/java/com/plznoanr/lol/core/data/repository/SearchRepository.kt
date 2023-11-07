package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.model.Search
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearchList(): Flow<List<Search>>

    suspend fun upsertSearch(search: Search)

    suspend fun deleteSearch(sName: String)

    suspend fun deleteSearchAll()

}