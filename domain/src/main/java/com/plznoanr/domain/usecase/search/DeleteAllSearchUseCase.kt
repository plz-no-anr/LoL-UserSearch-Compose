package com.plznoanr.domain.usecase.search

import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class DeleteAllSearchUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: AppRepository
): BaseUseCase<Unit, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return appRepository.deleteSearchAll()
    }

}