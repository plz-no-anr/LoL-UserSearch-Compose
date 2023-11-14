package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import javax.inject.Inject

class SaveBookmarkIdUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {

    suspend operator fun invoke(bookmarkId: String) {
        summonerRepository.updateBookmarkSummonerId(bookmarkId)
    }

}