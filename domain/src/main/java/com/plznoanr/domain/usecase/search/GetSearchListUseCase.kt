package com.plznoanr.domain.usecase.search

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.data.repository.SearchRepository
import com.plznoanr.model.Search
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchListUseCase@Inject constructor(
    private val searchRepository: SearchRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<Unit, List<Search>>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<List<Search>>> {
        return searchRepository.getSearchList().map { result ->
            Result.success(
                result.getOrThrow().sortedBy { it.date }
                    .map {
                        it.copy(
                            date = it.date
                        )
                    }
            )
        }
    }

}
