package com.plz.no.anr.lol.domain.model.common

import com.plz.no.anr.lol.domain.model.common.json.ChampionJson
import com.plz.no.anr.lol.domain.model.common.json.MapJson
import com.plz.no.anr.lol.domain.model.common.json.RuneJson
import com.plz.no.anr.lol.domain.model.common.json.SummonerJson

data class LocalJson(
    val champ: ChampionJson,
    val map: MapJson,
    val rune: List<RuneJson>,
    val summoner: SummonerJson
)
