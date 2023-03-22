package com.plznoanr.domain.usecase.base

import com.plznoanr.domain.extentions.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach

abstract class BaseUseCase<in P, R> {
    operator fun invoke(parameter: P): Flow<Result<R>> =
        execute(parameter)
            .onEach {
                ("${this.javaClass.simpleName}/$parameter/$it").log()
            }.catch { e ->
                e.printStackTrace()
                emit(Result.failure(e))
            }

    abstract fun execute(parameter: P): Flow<Result<R>>
}