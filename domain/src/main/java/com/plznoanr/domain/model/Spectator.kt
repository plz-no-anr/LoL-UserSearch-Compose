package com.plznoanr.domain.model

data class Spectator(
    val map: String,
    val banChamp: List<BanChamp>,
    val redTeam: SpectatorInfo,
    val blueTeam: SpectatorInfo
){
    data class BanChamp(
        val team: String,
        val champ: String
    )
    data class SpectatorInfo(
        val name: String,
        val champName: String,
        val champImg: String,
        val team: String,
        val spell1: String,
        val spell2: String,
        val runeStyle: Rune,
        val subStyle: Rune,
        val mainRune: String,
        val rune: List<Rune>
    ){
        data class Rune(
            val name:String,
            val icon:String
        )
    }
}





