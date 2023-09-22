package com.plz.no.anr.lol.data.model.common

sealed class AppError(val code: Int, val message: String) {

    object Default : AppError(0, "Default")
    object Network : AppError(1, "Network")
    object NotFound : AppError(404, "Not Found")
    object BadRequest : AppError(400, "Bad Request")
    object Unauthorized : AppError(401, "Unauthorized")
    object Forbidden : AppError(403, "Forbidden")
    object NotPlaying : AppError(1000, "Spectator is null")
    object NoMatchHistory : AppError(1001, "No Match History")
    object NoJsonData : AppError(1002, "Local Json is null")

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
    }

    fun exception() = Exception(this.toDescription())
}

fun String.parseError(): AppError = this.let {
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
}