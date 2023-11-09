package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @AppDispatchers.IO private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(name: String): Flow<Result<Summoner>> = flow {
        emit(summonerRepository.requestSummoner(name))
    }.onEach {
        summonerRepository.upsertSummoner(it.getOrThrow())
    }.catch {
        emit(Result.failure(it))
    }.flowOn(ioDispatcher)

}