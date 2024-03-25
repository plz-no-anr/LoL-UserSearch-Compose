package com.plznoanr.lol.core.common.model

sealed interface Result<out R> {

    data class Success<T>(val data: T): Result<T>

    data class Error(val error: AppError): Result<Nothing>

}

fun <R> Result<R>.isSuccess() = this is Result.Success

fun <R> Result<R>.isError() = this is Result.Error

fun <R> Result<R>.getOrThrow(): R = when (this) {
    is Result.Success -> data
    is Result.Error -> throw error.exception()
}

fun <R> Result<R>.getOrNull(): R? =
    if (isSuccess()) (this as Result.Success).data else null

inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
    if (isSuccess()) {
        action((this as Result.Success).data)
    }
    return this
}

inline fun <T> Result<T>.onError(action: (Result.Error) -> Unit): Result<T> {
    if (this is Result.Error) {
        action(this)
    }
    return this
}