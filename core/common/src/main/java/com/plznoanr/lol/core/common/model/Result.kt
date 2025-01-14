package com.plznoanr.lol.core.common.model

sealed interface Result<out R> {

    data class Success<T>(val data: T) : Result<T>

    sealed class Error(open val code: Int, open val message: String) : Result<Nothing>

    data class NetworkError(override val code: Int, override val message: String) :
        Error(code, message)

    data class ExceptionError(val exception: kotlin.Exception) : Error(
        code = 0,
        message = exception.message ?: ""
    )

    data object ApiKeyNotFoundError : Error(0, "Api Key Not Found")
    data object DefaultError : Error(0, "Default")
    data object NetworkConnectError : Error(1, "Network")
    data object NotFoundError : Error(404, "Not Found")
    data object BadRequestError : Error(400, "Bad Request")
    data object UnauthorizedError : Error(401, "Unauthorized")
    data object ForbiddenError : Error(403, "Forbidden")
    data object NotPlayingError : Error(1000, "Spectator is null")
    data object NoMatchHistoryError : Error(1001, "No Match History")
    data object NoJsonDataError : Error(1002, "Local Json is null")

    data object SummonerNullError : Error(1003, "Summoner is null")

    fun Error.parse() = "${this.code}/${this.message}"

    fun Error.description() = when (this) {
        is DefaultError -> "Unknown error"
        is NetworkConnectError -> "Please check your internet connection and try again"
        is NotFoundError -> "Summoner that doesn't exist"
        is BadRequestError -> "Bad request."
        is UnauthorizedError -> "The wrong approach."
        is ForbiddenError -> "This key has expired, please reissue"
        is NotPlayingError -> "The summoner is not in game"
        is NoMatchHistoryError -> "The summoner does not have a match for this season."
        is NoJsonDataError -> "Failed to load local json."
        is SummonerNullError -> "Summoner is null"
        else -> "${this.code} ${this.message}"
    }

    fun Error.exception() = Exception(this.parse())

}

fun <R> Result<R>.isSuccess() = this is Result.Success

fun <R> Result<R>.isError() = this is Result.Error

fun <R> Result<R>.getOrThrow(): R = when (this) {
    is Result.Success -> data
    is Result.Error -> throw exception()
}

fun <R> Result<R>.exceptionOrNull(): Throwable? =
    when (this) {
        is Result.Error -> this.exception()
        else -> null
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