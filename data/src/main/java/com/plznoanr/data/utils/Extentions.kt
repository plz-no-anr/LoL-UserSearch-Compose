package com.plznoanr.data.utils

fun String.parseError(code: Int) = "$code/$this"

fun Int.toIcon() = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/profileicon/$this.png"

fun String.toChampImage() = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/champion/$this"

fun String.toSpellImage() = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/spell/$this"