package com.plznoanr.domain.model.common

import com.plznoanr.domain.model.common.json.ChampionJson
import com.plznoanr.domain.model.common.json.MapJson
import com.plznoanr.domain.model.common.json.RuneJson
import com.plznoanr.domain.model.common.json.SummonerJson

data class LocalJson(
    val champ: ChampionJson,
    val map: MapJson,
    val rune: List<RuneJson>,
    val summoner: SummonerJson
)
