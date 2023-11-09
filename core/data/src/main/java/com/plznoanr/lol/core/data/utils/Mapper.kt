package com.plznoanr.lol.core.data.utils

import com.plznoanr.lol.core.data.model.ChampionJson
import com.plznoanr.lol.core.data.model.MapJson
import com.plznoanr.lol.core.data.model.RuneJson
import com.plznoanr.lol.core.data.model.SummonerJson
import com.plznoanr.lol.core.database.model.ProfileEntity
import com.plznoanr.lol.core.database.model.SearchEntity
import com.plznoanr.lol.core.database.model.SummonerEntity
import com.plznoanr.lol.core.database.model.json.ChampEntity
import com.plznoanr.lol.core.database.model.json.MapEntity
import com.plznoanr.lol.core.database.model.json.RuneEntity
import com.plznoanr.lol.core.database.model.json.SpellEntity
import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.model.Search
import com.plznoanr.lol.core.model.Summoner

internal fun Search.asEntity() = SearchEntity(
    name = name,
    date = date,
    isBookmark = isBookmark
)

internal fun Summoner.asEntity() = SummonerEntity(
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
    isBookMarked = isBookMarked
)

internal fun Summoner.MiniSeries.asEntity() = SummonerEntity.MiniSeries(
    losses = losses,
    wins = wins,
    target = target,
    progress = progress
)

internal fun Profile.asEntity() = ProfileEntity(
    id = id,
    name = name,
    level = level,
    icon = icon
)

internal fun ChampionJson.Champion.asEntity() = ChampEntity(
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

internal fun MapJson.MapData.asEntity() = MapEntity(
    mapId = mapId,
    mapName = mapName,
)

internal fun RuneJson.asEntity() = RuneEntity(
    id = id,
    key = key,
    icon = icon,
    name = name,
    slots = slots.map { it.asRuneInfoEntity() }
)

internal fun SummonerJson.Spell.asEntity() = SpellEntity(
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


private fun RuneJson.RuneInfo.asRuneInfoEntity() = RuneEntity.RuneInfo(
    runes = runes.map { it.asSubRuneEntity() }
)


private fun RuneJson.RuneInfo.SubRune.asSubRuneEntity() = RuneEntity.RuneInfo.SubRune(
    id = id,
    key = key,
    icon = icon,
    name = name
)
