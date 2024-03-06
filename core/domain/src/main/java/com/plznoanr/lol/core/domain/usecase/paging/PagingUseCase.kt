package com.plznoanr.lol.core.domain.usecase.paging

import com.plznoanr.lol.core.common.model.Paging
import com.plznoanr.lol.core.common.model.PagingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

abstract class PagingUseCase<T> {
    private val cachedList = mutableListOf<T>()
    private var cachedPaging = Paging(
        page = 0,
        size = 20,
        hasNext = true
    )

    val hasNext: Boolean
        get() = cachedPaging.hasNext

    fun execute(
        pageSize: Int = 20,
        function: suspend (Paging) -> PagingResult<T>,
    ): Flow<List<T>> = flow {
        emit(Unit)
    }.onEach {
        cachedPaging = cachedPaging.copy(
            size = pageSize
        )
    }.onEach {
        cachedPaging = cachedPaging.next()
    }.filter {
        cachedPaging.hasNext
    }.map {
        function(cachedPaging)
    }.onEach {
        cachedPaging = cachedPaging.copy(
            page = it.page,
            hasNext = it.hasNext
        )
        cachedList.addAll(it.data)
    }.map {
        cachedList.toList()
    }

    fun executeFlow(
        pageSize: Int = 20,
        function: (Paging) -> Flow<PagingResult<T>>
    ): Flow<List<T>> = flow {
        emit(Unit)
    }.onEach {
        cachedPaging = cachedPaging.copy(
            size = pageSize
        )
    }.onEach {
        cachedPaging = cachedPaging.next()
    }.filter {
        cachedPaging.hasNext
    }.flatMapConcat {
        function(cachedPaging)
    }.onEach {
        cachedPaging = cachedPaging.copy(
            page = it.page,
            hasNext = it.hasNext
        )
        cachedList.addAll(it.data)
    }.map {
        cachedList.toList()
    }

    fun clear() {
        cachedList.clear()
        cachedPaging = Paging(
            page = 0,
            size = 20,
            hasNext = true
        )
    }

}