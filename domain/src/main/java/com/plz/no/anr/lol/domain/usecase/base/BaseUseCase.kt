package com.plz.no.anr.lol.domain.usecase.base

import com.plz.no.anr.lol.domain.extentions.printLog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

abstract class BaseUseCase<in P, R>(
    private val coroutineDispatcher: CoroutineDispatcher
) {
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
            }.flowOn(coroutineDispatcher)

    abstract fun execute(parameter: P): Flow<Result<R>>
}