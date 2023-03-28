package com.plznoanr.data.utils

import com.plznoanr.data.model.local.ProfileEntity
import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.data.model.local.SummonerEntity
import com.plznoanr.data.model.local.json.ChampEntity
import com.plznoanr.data.model.local.json.MapEntity
import com.plznoanr.data.model.local.json.RuneEntity
import com.plznoanr.data.model.local.json.SpellEntity
import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Search
import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.model.common.ChampionJson
import com.plznoanr.domain.model.common.MapJson
import com.plznoanr.domain.model.common.RuneJson
import com.plznoanr.domain.model.common.SummonerJson


fun Search.toEntity() = SearchEntity(
    name = name,
    date = date
)

fun Summoner.toEntity() = SummonerEntity(
    name = name,
    level = level,
    icon = icon,
    tier = tier,
    leaguePoints = leaguePoints,
    rank = rank,
    wins = wins,
    losses = losses,
    miniSeries = miniSeries?.toEntity(),
    isPlaying = isPlaying
)

fun Summoner.MiniSeries.toEntity() = SummonerEntity.MiniSeries(
    losses = losses,
    wins = wins,
    target = target,
    progress = progress
)

fun Profile.toEntity() = ProfileEntity(
    name = name,
    level = level,
    icon = icon
)

fun ChampionJson.Champion.toEntity() = ChampEntity(
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

fun MapJson.MapData.toEntity() = MapEntity(
    mapId = mapId,
    mapName = mapName,
)

fun RuneJson.toEntity() = RuneEntity(
    id = id,
    key = key,
    icon = icon,
    name = name,
    slots = slots.toEntity()
)

fun SummonerJson.Spell.toEntity() = SpellEntity(
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


private fun List<RuneJson.RuneInfo>.toEntity() = map { it.toEntity1() }

private fun RuneJson.RuneInfo.toEntity1() = RuneEntity.RuneInfo(
    runes = runes.toEntity2()
)

private fun List<RuneJson.RuneInfo.SubRune>.toEntity2() = map { it.toEntity3() }

private fun RuneJson.RuneInfo.SubRune.toEntity3() = RuneEntity.RuneInfo.SubRune(
    id = id,
    key = key,
    icon = icon,
    name = name
)
