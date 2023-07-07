package com.plz.no.anr.lol.data.model.remote

import com.plz.no.anr.lol.domain.model.Summoner
@kotlinx.serialization.Serializable
internal data class LeagueResponse(
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

internal fun LeagueResponse.MiniSeriesDTO.toDomain() = Summoner.MiniSeries(
    losses = this.losses,
    target = this.target,
    wins = this.wins,
    progress = this.progress
)