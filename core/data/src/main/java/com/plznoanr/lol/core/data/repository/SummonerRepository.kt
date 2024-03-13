package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.common.model.Paging
import com.plznoanr.lol.core.common.model.PagingResult
import com.plznoanr.lol.core.common.model.Result
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.model.Spectator
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.flow.Flow

interface SummonerRepository {

    suspend fun requestSummoner(nickname: Nickname): Result<Summoner>

    fun getSummoner(summonerId: String): Flow<Summoner?>

    fun getSummonerAll(): Flow<List<Summoner>>

    fun getSummonerList(paging: Paging): Flow<PagingResult<Summoner>>

    fun getBookMarkedSummonerIds(): Flow<Set<String>>

    suspend fun requestSpectator(summonerId: String): Result<Spectator>

    suspend fun upsertSummoner(summoner: Summoner)

    suspend fun updateBookmarkSummonerId(id: String)

    suspend fun clearBookmark()

    suspend fun deleteSummoner(name: String)

    suspend fun deleteSummonerAll()

}