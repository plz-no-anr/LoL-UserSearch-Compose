package com.plznoanr.domain.usecase.json

import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class InitialLocalJsonUseCase(
    private val repository: AppRepository
) : BaseUseCase<Unit, Boolean>() {
    override fun execute(parameter: Unit): Flow<Result<Boolean>> {
        return repository.initLocalJson()
    }

}