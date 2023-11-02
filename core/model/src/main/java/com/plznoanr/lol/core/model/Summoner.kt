package com.plznoanr.lol.core.model

data class Summoner(
    val id: String, // 소환사 아이디
    val name: String, // 소환사 이름
    val level: String, // 레벨
    val icon: String, // 소환사 아이콘
    val tier: String, // 티어 (GOLD)
    val leaguePoints: Int, // 점수
    val rank: String, // 랭크 (I)
    val wins: Int, // 승리
    val losses: Int, // 패배
    val miniSeries: MiniSeries? = null, // 승급전
) {
    data class MiniSeries(
        var losses: Int,
        var target: Int,
        var wins: Int,
        var progress: String
    )

    val levelInfo = "LV: $level"

    val lpWinLose = "$leaguePoints LP / ${wins}승 ${losses}패"

    val tierRank = "$tier $rank"

    fun asProfile() = Profile(
        id = id,
        name = name,
        level = level,
        icon = icon,
    )

}

fun getDummySummoner() = Summoner(
    id = "Summoner Id",
    name = "Summoner Name",
    level = "100",
    icon = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/profileicon/6.png",
    tier = "GRANDMASTER",
    rank = "I",
    leaguePoints = 322,
    wins = 223,
    losses = 203,
    miniSeries = Summoner.MiniSeries(
        progress = "WLNNN",
        wins = 1,
        losses = 1,
        target = 3
    )
)
