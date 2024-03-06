package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {

    operator fun invoke(nickname: Nickname): Flow<Summoner> =
        summonerRepository.getSummoner(nickname.name).map { local ->
            val remote = summonerRepository.requestSummoner(nickname).getOrNull()?.also {
                summonerRepository.upsertSummoner(it)
            }
            remote ?: local
        }.map {
            it ?: throw Exception("Summoner is null")
        }

}