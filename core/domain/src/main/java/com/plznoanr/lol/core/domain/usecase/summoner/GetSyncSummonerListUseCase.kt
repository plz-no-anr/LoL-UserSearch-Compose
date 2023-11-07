package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.common.model.Paging
import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

class GetSyncSummonerListUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @AppDispatchers.IO private val ioDispatcher: CoroutineDispatcher
) {
    private val infiniteRepeater: Flow<Int> = flow {
        repeat(Int.MAX_VALUE) {
            emit(it)
            delay(5.minutes)
        }
    }

    operator fun invoke(parameter: Paging): Flow<List<Summoner>> {
        return combine(
            infiniteRepeater,
            summonerRepository.getSummonerList(parameter)
        ) { _, summonerList ->
            summonerList.map { summoner ->
                summonerRepository.requestSummoner(summoner.name).getOrNull() ?: summoner
            }
        }.map {
          it.sortedBy { summoner -> summoner.isBookMarked }
        }.flowOn(ioDispatcher)
    }

}