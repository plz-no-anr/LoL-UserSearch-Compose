package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import javax.inject.Inject

class DeleteSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {

    suspend operator fun invoke(parameter: String) {
        return summonerRepository.deleteSummoner(parameter)
    }

}