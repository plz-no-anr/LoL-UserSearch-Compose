package com.plz.no.anr.lol.data.repository.local.search

import com.plz.no.anr.lol.data.model.local.SearchEntity

interface SearchLocalDataSource {

    suspend fun getSearch() : List<SearchEntity>

    suspend fun insertSearch(searchEntity: SearchEntity)

    suspend fun deleteSearch(sName: String)

    suspend fun updateSearch(searchEntity: SearchEntity)

    suspend fun deleteSearchAll()

}