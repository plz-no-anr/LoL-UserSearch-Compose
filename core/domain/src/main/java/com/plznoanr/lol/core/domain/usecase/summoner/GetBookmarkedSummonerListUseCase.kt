package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.data.repository.SummonerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBookmarkedSummonerListUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {
    operator fun invoke() = summonerRepository.getSummonerAll()
        .map { summoners ->
            val bookmarkIds = summonerRepository.getBookMarkedSummonerIds().first()
            summoners.map {
                it.copy(
                    isBookMarked = bookmarkIds.contains(it.id)
                )
            }
        }.map {
            it.filter { summoner -> summoner.isBookMarked }
        }

}