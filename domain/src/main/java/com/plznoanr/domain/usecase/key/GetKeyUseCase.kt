package com.plznoanr.domain.usecase.key

import com.plznoanr.domain.repository.AppRepository

class GetKeyUseCase(
    private val repository: AppRepository
) {
    operator fun invoke(parameter: Unit): String = repository.apiKey

}