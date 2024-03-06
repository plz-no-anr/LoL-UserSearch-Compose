package com.plznoanr.lol.core.network.model

@kotlinx.serialization.Serializable
data class SummonerResponse(
    val accountId: String,
    val profileIconId: Int,
    val revisionDate: Long,
    val name: String,
    val id: String,
    val puuid: String,
    val summonerLevel: Long
)