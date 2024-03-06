package com.plznoanr.lol.core.model

data class Summoner(
    val id: String, // 소환사 아이디
    val nickname: Nickname,
    val level: String, // 레벨
    val icon: String, // 소환사 아이콘
    val tier: String, // 티어 (GOLD)
    val leaguePoints: Int, // 점수
    val rank: String, // 랭크 (I)
    val wins: Int, // 승리
    val losses: Int, // 패배
    val miniSeries: MiniSeries? = null, // 승급전
    val isBookMarked: Boolean = false
) {
    data class MiniSeries(
        val losses: Int,
        val target: Int,
        val wins: Int,
        val progress: String
    )

    val levelInfo = "LV: $level"

    val lpWinLose = "$leaguePoints LP / ${wins}승 ${losses}패"

    val tierRank = "$tier $rank"

    fun asProfile() = Profile(
        id = id,
        name = nickname.toText(),
        level = level,
        icon = icon,
    )

}

fun getDummySummoner(index: Int = 0) = Summoner(
    id = "Summoner Id-$index",
    nickname = getDummyName(),
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
