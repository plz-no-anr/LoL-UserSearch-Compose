package com.plznoanr.lol.core.data.utils

import com.plznoanr.lol.core.common.model.Result


suspend fun <T> safeApiCall(
    apiCall: suspend () -> Result<T>
): Result<T> = try {
    apiCall()
} catch (e: Exception) {
    e.toError()
}

private fun Exception.toError() = Result.ExceptionError(this)