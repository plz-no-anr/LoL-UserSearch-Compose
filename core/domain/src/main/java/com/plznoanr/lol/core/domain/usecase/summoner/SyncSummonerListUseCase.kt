package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.di.AppDispatcher
import com.plznoanr.lol.core.common.di.Dispatcher
import com.plznoanr.lol.core.common.model.onError
import com.plznoanr.lol.core.common.model.onSuccess
import com.plznoanr.lol.core.data.repository.SummonerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

class SyncSummonerListUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) {
    private val infiniteRepeater: Flow<Int> = flow {
        repeat(Int.MAX_VALUE) {
            emit(it)
            delay(10.minutes)
        }
    }

    operator fun invoke(): Flow<Boolean> {
        return infiniteRepeater
            .onEach { Timber.d("Sync Count -> $it") }
            .map {
                val summonerList = summonerRepository.getSummonerAll().first()
                summonerList.forEach { summoner ->
                    val response = summonerRepository.requestSummoner(summoner.nickname)
                    response.onSuccess {
                        summonerRepository.upsertSummoner(it)
                    }.onError {
                        Timber.e("Summoner Sync: $it")
//                        throw it.error.exception()
                    }
                    delay(500L)
                }
                true
            }.catch {
                Timber.d(it)
//                emit(false)
            }.flowOn(ioDispatcher)
    }

}