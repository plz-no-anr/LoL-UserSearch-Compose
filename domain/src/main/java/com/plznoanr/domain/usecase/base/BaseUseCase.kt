package com.plznoanr.domain.usecase.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class BaseUseCase<in P, R> {
    operator fun invoke(parameter: P) : Flow<Result<R>> =
        execute(parameter)
            .catch { e ->
                e.printStackTrace()
                emit(Result.failure(e))
            }

    abstract fun execute(parameter: P) : Flow<Result<R>>
}