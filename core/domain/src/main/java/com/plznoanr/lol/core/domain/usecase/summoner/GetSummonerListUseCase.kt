package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.model.Paging
import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetSummonerListUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    private val refreshSummonerListUseCase: RefreshSummonerListUseCase
) {
    private val cachedList = mutableListOf<Summoner>()
    private var paging = Paging()

    operator fun invoke(isRefresh: Boolean = false): Flow<List<Summoner>> = flow {
        paging = paging.prev()
        emit(Unit)
    }.onEach { // 페이징 처리
        if (isRefresh) {
            refreshSummonerListUseCase()
            clear()
        } else {
            paging = paging.next()
        }
    }.filter { paging.hasNext }
        .flatMapLatest {
            summonerRepository.getSummonerList(paging)
        }
        .distinctUntilChanged()
        .onEach {
            paging = paging.copy(
                page = it.page,
                hasNext = it.hasNext
            )
            cachedList.addAll(it.data)
        }.map {
            cachedList.sortedByDescending { summoner -> summoner.isBookMarked }
        }

    private fun clear() {
        cachedList.clear()
        paging = Paging()
    }

}