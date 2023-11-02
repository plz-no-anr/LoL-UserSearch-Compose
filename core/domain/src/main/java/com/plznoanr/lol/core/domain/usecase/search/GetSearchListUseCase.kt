package com.plznoanr.lol.core.domain.usecase.search

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.domain.usecase.base.BaseUseCase
import com.plznoanr.lol.core.data.repository.SearchRepository
import com.plznoanr.lol.core.model.Search
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchListUseCase @Inject constructor(
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
