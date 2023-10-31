package com.plznoanr.data.repository.local.search

import com.plznoanr.data.model.local.SearchEntity
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {

    fun getSearch() : Flow<List<SearchEntity>?>

    suspend fun insertSearch(searchEntity: SearchEntity)

    suspend fun deleteSearch(sName: String)

    suspend fun updateSearch(searchEntity: SearchEntity)

    suspend fun deleteSearchAll()

}