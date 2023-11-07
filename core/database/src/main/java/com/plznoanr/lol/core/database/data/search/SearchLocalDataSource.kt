package com.plznoanr.lol.core.database.data.search

import com.plznoanr.lol.core.database.model.SearchEntity
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {

    fun getSearch() : Flow<List<SearchEntity>?>

    suspend fun upsertSearch(searchEntity: SearchEntity)

//    suspend fun insertSearch(searchEntity: SearchEntity)

    suspend fun deleteSearch(sName: String)

//    suspend fun updateSearch(searchEntity: SearchEntity)

    suspend fun deleteSearchAll()

}