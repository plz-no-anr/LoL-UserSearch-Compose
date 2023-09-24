package com.plz.no.anr.lol.data.utils

import com.plz.no.anr.lol.data.model.local.ProfileEntity
import com.plz.no.anr.lol.data.model.local.SearchEntity
import com.plz.no.anr.lol.data.model.local.SummonerEntity
import com.plz.no.anr.lol.data.model.local.asDomain
import com.plz.no.anr.lol.data.model.local.json.ChampEntity
import com.plz.no.anr.lol.data.model.local.json.MapEntity
import com.plz.no.anr.lol.data.model.local.json.RuneEntity
import com.plz.no.anr.lol.data.model.local.json.SpellEntity
import com.plz.no.anr.lol.domain.model.Profile
import com.plz.no.anr.lol.domain.model.Search
import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.model.common.json.ChampionJson
import com.plz.no.anr.lol.domain.model.common.json.MapJson
import com.plz.no.anr.lol.domain.model.common.json.RuneJson
import com.plz.no.anr.lol.domain.model.common.json.SummonerJson


fun Search.asEntity() = SearchEntity(
    name = name,
    date = date
)

fun List<SearchEntity>.asSearchList() = map { it.asDomain() }

fun Summoner.asEntity() = SummonerEntity(
    name = name,
    level = level,
    icon = icon,
    tier = tier,
    leaguePoints = leaguePoints,
    rank = rank,
    wins = wins,
    losses = losses,
    miniSeries = miniSeries?.asEntity(),
)

fun List<SummonerEntity>.asSummonerList() = map { it.asDomain() }

fun Summoner.MiniSeries.asEntity() = SummonerEntity.MiniSeries(
    losses = losses,
    wins = wins,
    target = target,
    progress = progress
)

fun Profile.asEntity() = ProfileEntity(
    name = name,
    level = level,
    icon = icon
)

fun ChampionJson.Champion.asEntity() = ChampEntity(
    id = id,
    key = key,
    name = name,
    title = title,
    image = ChampEntity.Image(
        full = image.full,
        sprite = image.sprite,
        group = image.group
    )
)

fun MapJson.MapData.asEntity() = MapEntity(
    mapId = mapId,
    mapName = mapName,
)

fun RuneJson.asEntity() = RuneEntity(
    id = id,
    key = key,
    icon = icon,
    name = name,
    slots = slots.asEntity()
)

fun SummonerJson.Spell.asEntity() = SpellEntity(
    id = id,
    key = key,
    name = name,
    description = description,
    tooltip = tooltip,
    image = SpellEntity.Image(
        full = image.full,
        sprite = image.sprite,
        group = image.group
    )
)


private fun List<RuneJson.RuneInfo>.asEntity() = map { it.asRuneInfo() }

private fun RuneJson.RuneInfo.asRuneInfo() = RuneEntity.RuneInfo(
    runes = runes.asSubRuneList()
)

private fun List<RuneJson.RuneInfo.SubRune>.asSubRuneList() = map { it.asSubRune() }

private fun RuneJson.RuneInfo.SubRune.asSubRune() = RuneEntity.RuneInfo.SubRune(
    id = id,
    key = key,
    icon = icon,
    name = name
)
