package com.plznoanr.domain.usecase.search

import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class DeleteSearchUseCase(
    private val appRepository: AppRepository
): BaseUseCase<String, Unit>() {

    override fun execute(parameter: String): Flow<Result<Unit>> {
        return appRepository.deleteSearch(parameter)
    }

}