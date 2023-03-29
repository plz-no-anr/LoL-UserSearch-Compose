package com.plznoanr.domain.usecase.key

import com.plznoanr.domain.extentions.asFlow
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class InsertKeyUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: AppRepository
): BaseUseCase<String, Unit>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Unit>> {
        repository.apiKey = parameter
        return Result.success(Unit).asFlow()
    }

}