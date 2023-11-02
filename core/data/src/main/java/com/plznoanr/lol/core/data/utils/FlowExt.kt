package com.plznoanr.lol.core.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import timber.log.Timber

fun <T> Flow<Result<T>>.catchResultError(): Flow<Result<T>> {
    return catch { e ->
        Timber.w("catchResultError : $e")
        emit(
            Result.failure(e)
        )
    }
}