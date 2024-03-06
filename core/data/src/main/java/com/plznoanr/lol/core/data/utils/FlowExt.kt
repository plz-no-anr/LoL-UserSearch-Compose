package com.plznoanr.lol.core.data.utils

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Result<T>
): Result<T> = try {
    apiCall()
} catch (e: Exception) {
    Result.failure(e)
}
