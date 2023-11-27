package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {

    operator fun invoke(summonerName: String): Flow<Summoner> =
        summonerRepository.getSummoner(summonerName).map { local ->
            val remote = summonerRepository.requestSummoner(summonerName).getOrNull()?.also {
                summonerRepository.upsertSummoner(it)
            }
            remote ?: local
        }.map {
            it ?: throw Exception("Summoner is null")
        }

}