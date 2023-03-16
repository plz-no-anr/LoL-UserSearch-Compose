package com.plznoanr.domain.usecase.search

import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class DeleteAllSearchUseCase(
    private val appRepository: AppRepository
): BaseUseCase<Unit, Unit>() {

    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return appRepository.deleteSearchAll()
    }

}