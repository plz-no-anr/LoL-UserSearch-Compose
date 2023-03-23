package com.plznoanr.domain.usecase.base

import com.plznoanr.domain.extentions.printLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach

abstract class BaseUseCase<in P, R> {
    operator fun invoke(parameter: P): Flow<Result<R>> =
        execute(parameter)
            .onEach {
                printLog(
                    useCaseName = this.javaClass.simpleName,
                    parameter = parameter.toString(),
                    result = it.toString()
                )
            }.catch { e ->
                e.printStackTrace()
                emit(Result.failure(e))
            }

    abstract fun execute(parameter: P): Flow<Result<R>>
}