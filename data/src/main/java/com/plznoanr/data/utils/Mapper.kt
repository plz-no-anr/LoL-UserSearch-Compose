package com.plznoanr.data.utils

import com.plznoanr.data.model.local.ProfileEntity
import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.data.model.local.SummonerEntity
import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Search
import com.plznoanr.domain.model.Summoner


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
