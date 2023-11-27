package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Summoner
import javax.inject.Inject

class RequestSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
) {

    suspend operator fun invoke(name: String): Result<Summoner> {
        val summoner = summonerRepository.requestSummoner(name)
        summoner.onSuccess { summonerRepository.upsertSummoner(it) }
        return summoner
    }

}