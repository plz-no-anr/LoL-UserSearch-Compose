package com.plznoanr.domain.usecase.search

import com.plznoanr.domain.model.Search
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class GetSearchUseCase(
    private val repository: AppRepository
): BaseUseCase<Unit, List<Search>>() {
    override fun execute(parameter: Unit): Flow<Result<List<Search>>> {
        return repository.getSearchList()
    }

}
