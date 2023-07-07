package com.plz.no.anr.lol.domain.model

data class Profile(
    val name: String,
    val level: String,
    val icon: String
) {
    fun getLevels() = "LV: $level"
}