package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {

    suspend operator fun invoke(summoner: Summoner) {
        summonerRepository.upsertSummoner(summoner)
    }

}