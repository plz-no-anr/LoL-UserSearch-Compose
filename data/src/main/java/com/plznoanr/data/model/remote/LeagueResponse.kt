package com.plznoanr.data.model.remote

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
    val miniSeries: MiniSeriesDTO?

){
    data class MiniSeriesDTO(
        val losses: Int,
        val target: Int,
        val wins: Int,
        val progress: String
    )

}
