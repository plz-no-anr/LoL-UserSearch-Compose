package com.plznoanr.lol.core.model

data class Nickname(
    val name: String,
    val tag: String
)

fun Nickname.toText() = "$name#$tag"

fun getDummyName() = Nickname("구우", "sad")