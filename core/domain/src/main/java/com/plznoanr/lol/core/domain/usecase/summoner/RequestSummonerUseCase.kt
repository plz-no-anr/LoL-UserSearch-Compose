package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.model.Summoner
import javax.inject.Inject

class RequestSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
) {

    suspend operator fun invoke(nickname: Nickname): Result<Summoner> {
        val summoner = summonerRepository.requestSummoner(nickname)
        summoner.onSuccess { summonerRepository.upsertSummoner(it) }
        return summoner
    }

}