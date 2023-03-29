package com.plznoanr.domain.usecase.json

import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class InitialLocalJsonUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: AppRepository
) : BaseUseCase<Unit, Boolean>(coroutineDispatcher) {
    override fun execute(parameter: Unit): Flow<Result<Boolean>> {
        return repository.initLocalJson()
    }

}