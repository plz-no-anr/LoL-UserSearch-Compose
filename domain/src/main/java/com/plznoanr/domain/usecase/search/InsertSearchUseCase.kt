package com.plznoanr.domain.usecase.search

import com.plznoanr.domain.model.Search
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class InsertSearchUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: AppRepository
): BaseUseCase<Search, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Search): Flow<Result<Unit>> {
        return appRepository.insertSearch(parameter)
    }

}
