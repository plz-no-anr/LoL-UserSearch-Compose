package com.plznoanr.lol.core.common.model

sealed class AppError(open val code: Int, open val message: String) {

    data class Retrofit(override val code: Int, override val message: String): AppError(code, message)

    data class Exception(val exception: kotlin.Exception): AppError(
        code = 0,
        message = exception.message ?: ""
    )

    data object Default : AppError(0, "Default")
    data object Network : AppError(1, "Network")
    data object NotFound : AppError(404, "Not Found")
    data object BadRequest : AppError(400, "Bad Request")
    data object Unauthorized : AppError(401, "Unauthorized")
    data object Forbidden : AppError(403, "Forbidden")
    data object NotPlaying : AppError(1000, "Spectator is null")
    data object NoMatchHistory : AppError(1001, "No Match History")
    data object NoJsonData : AppError(1002, "Local Json is null")

    data object SummonerNull: AppError(1003, "Summoner is null")

    fun parse() = "${this.code}/${this.message}"

    fun toDescription() = when (this) {
        is Default -> "Unknown error"
        is Network -> "Please check your internet connection and try again"
        is NotFound -> "Summoner that doesn't exist"
        is BadRequest -> "Bad request."
        is Unauthorized -> "The wrong approach."
        is Forbidden -> "This key has expired, please reissue"
        is NotPlaying -> "The summoner is not in game"
        is NoMatchHistory -> "The summoner does not have a match for this season."
        is NoJsonData -> "Failed to load local json."
        is SummonerNull -> "Summoner is null"
        else -> "${this.code} ${this.message}"
    }

    fun exception() = Exception(this.parse())
}

fun String?.parseError(): AppError = this?.let {
    if (it.contains("/")) it.split("/")[0].toInt().let { code ->
        when (code) {
            0 -> AppError.Network
            404 -> AppError.NotFound
            400 -> AppError.BadRequest
            401 -> AppError.Unauthorized
            403 -> AppError.Forbidden
            1000 -> AppError.NotPlaying
            1001 -> AppError.NoMatchHistory
            1002 -> AppError.NoJsonData
            else -> AppError.Default
        }
    } else {
        AppError.Default
    }
} ?: AppError.Default