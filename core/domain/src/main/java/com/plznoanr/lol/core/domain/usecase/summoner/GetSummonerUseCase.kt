package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {

    operator fun invoke(name: String): Flow<Result<Summoner>> {
        return flow {
            emit(summonerRepository.requestSummoner(name))
        }.onEach {
            summonerRepository.upsertSummoner(it.getOrThrow())
        }
    }

}