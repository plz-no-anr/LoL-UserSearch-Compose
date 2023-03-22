package com.plz.no.anr.lol_usersearch_compose.ui.feature.common

enum class ErrorType(val msg: String) {
    NETWORK("Please check your internet connection and try again"),
    FORBIDDEN("This key has expired, please reissue"),
    NOT_FOUND("Summoner that doesn't exist"),
}