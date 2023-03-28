package com.plznoanr.domain.model.common

data class LocalJson(
    val champ: ChampionJson,
    val map: MapJson,
    val rune: List<RuneJson>,
    val summoner: SummonerJson
)
