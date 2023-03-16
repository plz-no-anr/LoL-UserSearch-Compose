package com.plznoanr.domain.model

data class Summoner(
    val name: String, // 소환사 이름
    val level: String, // 레벨
    val icon: String, // 소환사 아이콘
    val tier: String, // 티어 (GOLD)
    val leaguePoints: Int, // 점수
    val rank: String, // 랭크 (I)
    val wins: Int, // 승리
    val losses: Int, // 패배
    val miniSeries: MiniSeries?, // 승급전
    val isPlaying: Boolean,
) {
    data class MiniSeries(
        var losses: Int,
        var target: Int,
        var wins: Int,
        var progress: String
    )

    fun getLevels() = "LV: $level"

    fun getLeaguePoint() = "$leaguePoints LP / ${wins}승 ${losses}패"

    fun getTierRank() = "$tier $rank"

    fun getMiniVisible():String = if (miniSeries?.progress != "No") "visible" else "gone"

}

