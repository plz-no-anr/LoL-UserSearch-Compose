package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import javax.inject.Inject

class DeleteAllSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {

    suspend operator fun invoke() {
        summonerRepository.deleteSummonerAll()
    }

}