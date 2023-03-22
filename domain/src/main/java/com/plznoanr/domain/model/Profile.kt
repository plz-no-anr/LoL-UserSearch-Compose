package com.plznoanr.domain.model

data class Profile(
    val name: String,
    val level: String,
    val icon: String
) {
    fun getLevels() = "LV: $level"
}