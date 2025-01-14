package com.plznoanr.lol.core.domain.usecase.paging

import com.plznoanr.lol.core.common.model.Paging
import com.plznoanr.lol.core.common.model.PagingList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

abstract class PagingUseCase<T> {
    private val cachedSet = mutableSetOf<T>()
    private var cachedPaging = Paging(
        page = 0,
        size = 20,
        hasNext = true
    )

    val hasNext: Boolean
        get() = cachedPaging.hasNext

//    fun execute(
//        pageSize: Int = 20,
//        function: suspend (Paging) -> PagingResult<T>,
//    ): Flow<List<T>> = flow {
//        emit(Unit)
//    }.onEach {
//        cachedPaging = cachedPaging.copy(
//            size = pageSize
//        )
//    }.onEach {
//        cachedPaging = cachedPaging.next()
//    }.filter {
//        cachedPaging.hasNext
//    }.map {
//        function(cachedPaging)
//    }.onEach {
//        cachedPaging = cachedPaging.copy(
//            page = it.page,
//            hasNext = it.hasNext
//        )
//        cachedSet.addAll(it.data)
//    }.map {
//        cachedSet.toList()
//    }

    fun executeFlow(
        pageSize: Int = 20,
        isClear: Boolean = false,
        function: (Paging) -> Flow<PagingList<T>>
    ): Flow<List<T>> = flow {
        emit(Unit)
    }.onEach {
        if (isClear) clear()
    }.onEach {
        cachedPaging = cachedPaging.copy(
            size = pageSize
        )
    }.onEach {
        cachedPaging = cachedPaging.next()
    }.filter {
        cachedPaging.hasNext
        true
    }.flatMapLatest {
        function(cachedPaging)
    }.onEach {
        if (it.page <= 1 && it.data.isEmpty()) clear()
    }.onEach {
        cachedPaging = cachedPaging.copy(
            page = it.page,
            hasNext = it.hasNext
        )
        cachedSet.addAll(it.data)
    }.map {
        cachedSet.toList()
    }

    private fun clear() {
        cachedSet.clear()
        cachedPaging = Paging(
            page = 0,
            size = 20,
            hasNext = true
        )
    }

}