package com.plznoanr.data.model.remote

import com.plznoanr.model.Summoner
@kotlinx.serialization.Serializable
data class LeagueResponse(
    val leagueId: String,
    val summonerId: String,
    val summonerName: String,
    val queueType: String,
    val tier: String,
    val rank: String,
    val leaguePoints: Int,
    val wins: Int,
    val losses: Int,
    val hotStreak: Boolean,
    val veteran: Boolean,
    val freshBlood: Boolean,
    val inactive: Boolean,
    val miniSeries: MiniSeriesDTO? = null
) {
    @kotlinx.serialization.Serializable
    data class MiniSeriesDTO(
        val losses: Int,
        val target: Int,
        val wins: Int,
        val progress: String
    )
}

fun LeagueResponse.MiniSeriesDTO.asDomain() = Summoner.MiniSeries(
    losses = this.losses,
    target = this.target,
    wins = this.wins,
    progress = this.progress
)