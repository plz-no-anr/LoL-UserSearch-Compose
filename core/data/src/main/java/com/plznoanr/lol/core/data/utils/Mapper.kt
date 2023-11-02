package com.plznoanr.lol.core.data.utils

import com.plznoanr.lol.core.database.model.ProfileEntity
import com.plznoanr.lol.core.database.model.SearchEntity
import com.plznoanr.lol.core.database.model.SummonerEntity
import com.plznoanr.lol.core.database.model.asDomain
import com.plznoanr.lol.core.database.model.json.ChampEntity
import com.plznoanr.lol.core.database.model.json.MapEntity
import com.plznoanr.lol.core.database.model.json.RuneEntity
import com.plznoanr.lol.core.database.model.json.SpellEntity
import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.model.Search
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.model.common.json.ChampionJson
import com.plznoanr.lol.core.model.common.json.MapJson
import com.plznoanr.lol.core.model.common.json.RuneJson
import com.plznoanr.lol.core.model.common.json.SummonerJson


fun Search.asEntity() = SearchEntity(
    name = name,
    date = date
)

fun List<SearchEntity>.asSearchList() = map { it.asDomain() }

fun Summoner.asEntity() = SummonerEntity(
    id = id,
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
    id = id,
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
