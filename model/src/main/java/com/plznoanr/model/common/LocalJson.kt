package com.plznoanr.model.common

import com.plznoanr.model.common.json.ChampionJson
import com.plznoanr.model.common.json.MapJson
import com.plznoanr.model.common.json.RuneJson
import com.plznoanr.model.common.json.SummonerJson

data class LocalJson(
    val champ: ChampionJson,
    val map: MapJson,
    val rune: List<RuneJson>,
    val summoner: SummonerJson
)
