package com.plz.no.anr.lol.data.repository.local.search

import com.plz.no.anr.lol.data.model.local.SearchEntity
import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {

    fun getSearch() : Flow<List<SearchEntity>>

    suspend fun insertSearch(searchEntity: SearchEntity)

    suspend fun deleteSearch(sName: String)

    suspend fun updateSearch(searchEntity: SearchEntity)

    suspend fun deleteSearchAll()

}