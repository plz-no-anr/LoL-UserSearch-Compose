package com.plz.no.anr.lol_usersearch_compose.ui.feature.common.error

enum class ErrorType(val msg: String) {
    NETWORK("Please check your internet connection and try again"),
    FORBIDDEN("This key has expired, please reissue"),
    NOT_FOUND("Summoner that doesn't exist"),
    NOT_PLAYING("The summoner is not in game"),
    NO_MATCH_HISTORY("The summoner does not have a match for this season."),
    Unauthorized("The wrong approach."),
}