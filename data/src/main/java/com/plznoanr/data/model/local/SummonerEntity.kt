package com.plznoanr.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plznoanr.model.Summoner

@Entity(tableName = "Summoner")
data class SummonerEntity(
    @PrimaryKey
    val id: String, // 소환사 아이디
    val name: String, // 소환사 이름
    val level: String, // 레벨
    val icon: String, // 소환사 아이콘
    val tier: String, // 티어 (GOLD)
    val leaguePoints: Int, // 점수
    val rank: String, // 랭크 (I)
    @ColumnInfo(name = "total_wins")
    val wins: Int, // 승리
    @ColumnInfo(name = "total_losses")
    val losses: Int, // 패배
    @Embedded val miniSeries: MiniSeries?, // 승급전
) {
    data class MiniSeries(
        val losses: Int,
        val target: Int,
        val wins: Int,
        val progress: String
    )
    fun MiniSeries.asDomain() = Summoner.MiniSeries(
        losses = losses,
        target = target,
        wins = wins,
        progress = progress
    )
}

fun SummonerEntity.asDomain() = Summoner(
    id = id,
    name = name,
    level = level,
    icon = icon,
    tier = tier,
    leaguePoints = leaguePoints,
    rank = rank,
    wins = wins,
    losses = losses,
    miniSeries = miniSeries?.asDomain(),
)