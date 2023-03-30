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
import com.plznoanr.domain.model.common.json.ChampionJson
import com.plznoanr.domain.model.common.json.MapJson
import com.plznoanr.domain.model.common.json.RuneJson
import com.plznoanr.domain.model.common.json.SummonerJson


fun Search.toEntity() = SearchEntity(
    name = name,
    date = date
)

fun List<SearchEntity>.toSearchList() = map { it.toDomain() }

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

fun List<SummonerEntity>.toSummonerList() = map { it.toDomain() }

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


private fun List<RuneJson.RuneInfo>.toEntity() = map { it.toRuneInfo() }

private fun RuneJson.RuneInfo.toRuneInfo() = RuneEntity.RuneInfo(
    runes = runes.toSubRuneList()
)

private fun List<RuneJson.RuneInfo.SubRune>.toSubRuneList() = map { it.toSubRune() }

private fun RuneJson.RuneInfo.SubRune.toSubRune() = RuneEntity.RuneInfo.SubRune(
    id = id,
    key = key,
    icon = icon,
    name = name
)
