package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.model.Paging
import com.plznoanr.lol.core.common.model.PagingResult
import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Spectator
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSummonerRepository(
    private val summoners: List<Summoner>,
    private val bookmarkedSummonerIds: Set<String>
) : SummonerRepository {
    override suspend fun requestSummoner(name: String): Result<Summoner> {
        TODO("Not yet implemented")
    }

    override fun getSummoner(summonerName: String): Flow<Summoner?> {
        return flow { emit(summoners.first { it.nickname == summonerName }) }
    }

    override fun getSummonerAll(): Flow<List<Summoner>> {
        return flow { emit(summoners) }
    }

    override fun getSummonerList(paging: Paging): Flow<PagingResult<Summoner>> {
        return flow {
            emit(
                PagingResult(
                    data = summoners,
                    page = paging.page
                )
            )
        }
    }

    override fun getBookMarkedSummonerIds(): Flow<Set<String>> {
        return flow { emit(bookmarkedSummonerIds) }
    }

    override suspend fun requestSpectator(summonerId: String): Result<Spectator> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertSummoner(summoner: Summoner) {
        return
    }

    override suspend fun updateBookmarkSummonerId(id: String) {
        return
    }

    override suspend fun deleteSummoner(name: String) {
        return
    }

    override suspend fun deleteSummonerAll() {
        return
    }
}