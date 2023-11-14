package com.plznoanr.lol.core.domain.usecase.summoner

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBookmarkedSummonerListUseCase @Inject constructor(
    private val getSummonerListUseCase: GetSummonerListUseCase
) {
    operator fun invoke(isRefresh: Boolean = false) = getSummonerListUseCase(isRefresh).map {
        it.filter { summoner -> summoner.isBookMarked }
    }

}