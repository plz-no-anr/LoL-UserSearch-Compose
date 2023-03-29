package com.plznoanr.data.utils

object QueueType {
    const val SOLO_RANK = "RANKED_SOLO_5x5"
}

enum class Error(val code: Int, val message: String) {
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_PLAYING(1000, "Spectator is null"),
    NO_MATCH_HISTORY(1001, "No Match History"),
    NO_JSON_DATA(1002, "Local Json is null"),
}

object JsonFileName {
    const val CHAMPION = "champion.json"
    const val MAP = "map.json"
    const val SUMMONER = "summoner.json"
    const val RUNE = "runesReforged.json"
}