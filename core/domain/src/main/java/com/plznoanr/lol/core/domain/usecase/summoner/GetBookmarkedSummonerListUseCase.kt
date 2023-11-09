package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.model.Paging
import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetBookmarkedSummonerListUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {
    private val cachedList = mutableListOf<Summoner>()
    private var paging = Paging()

    operator fun invoke(isRefresh: Boolean = false) = flow {
        paging = paging.prev()
        emit(Unit)
    }.onEach {
        if (isRefresh) {
            clear()
        } else {
            paging = paging.next()
        }
    }.filter { paging.hasNext }
        .flatMapLatest {
            summonerRepository.getBookMarkedSummonerList(paging)
        }.map {
            paging = paging.copy(
                page = it.page,
                hasNext = it.hasNext
            )
            cachedList.addAll(it.data)
        }

    private fun clear() {
        cachedList.clear()
        paging = Paging()
    }

}