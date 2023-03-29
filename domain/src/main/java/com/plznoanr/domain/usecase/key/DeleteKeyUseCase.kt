package com.plznoanr.domain.usecase.key

import com.plznoanr.domain.extentions.asFlow
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class DeleteKeyUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: AppRepository
) : BaseUseCase<Unit, Unit>(coroutineDispatcher) {
    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        repository.apiKey = null
        return Result.success(Unit).asFlow()
    }

}