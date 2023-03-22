package com.plznoanr.domain.repository

import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Search
import com.plznoanr.domain.model.Summoner
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    var apiKey: String
    fun getSearchList(): Flow<Result<List<Search>>>
    fun insertSearch(search: Search): Flow<Result<Unit>>
    fun deleteSearch(sName: String): Flow<Result<Unit>>
    fun deleteSearchAll(): Flow<Result<Unit>>
    fun requestSummoner(name: String): Flow<Result<Summoner>>
    fun getSummonerList(): Flow<Result<List<Summoner>>>
    fun insertSummoner(summoner: Summoner): Flow<Result<Unit>>
    fun deleteSummoner(name: String): Flow<Result<Unit>>
    fun deleteSummonerAll(): Flow<Result<Unit>>
    fun getProfile(): Flow<Result<Profile>>
    fun insertProfile(profile: Profile): Flow<Result<Unit>>
    fun deleteProfile(): Flow<Result<Unit>>

}