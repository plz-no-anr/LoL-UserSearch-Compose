package com.plznoanr.model

data class Spectator(
    val map: String,
    val banChamp: List<BanChamp>,
    val redTeam: List<SpectatorInfo>,
    val blueTeam: List<SpectatorInfo>
){
    data class BanChamp(
        val team: Team,
        val champName: String
    )
    data class SpectatorInfo(
        val name: String,
        val champName: String,
        val champImg: String,
        val team: Team,
        val spell1: String,
        val spell2: String,
        val runeStyle: Rune,
        val subStyle: Rune,
        val mainRune: String,
        val rune: List<Rune>
    ){
        data class Rune(
            val name: String,
            val icon: String
        )
    }
}

enum class Team(val team: String) {
    RED("RED"),
    BLUE("BLUE")
}





