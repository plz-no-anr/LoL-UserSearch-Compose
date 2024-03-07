package com.plznoanr.lol.core.model

data class Profile(
    val id: String,
    val nickname: String,
    val level: String,
    val icon: String
) {
    val profileText: String
        get() = "$levelText $nickname"
    val levelText: String
        get() = "LV: $level"
}