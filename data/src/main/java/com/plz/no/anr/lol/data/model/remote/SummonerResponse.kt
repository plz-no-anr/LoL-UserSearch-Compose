package com.plz.no.anr.lol.data.model.remote

@kotlinx.serialization.Serializable
internal data class SummonerResponse(
    val accountId: String,
    val profileIconId: Int,
    val revisionDate: Long,
    val name: String,
    val id: String,
    val puuid: String,
    val summonerLevel: Long
)

//internal fun SummonerResponse.asDomain(): Summoner = Summoner(
//    accountId = accountId,
//    profileIconId = profileIconId,
//    revisionDate = revisionDate,
//    name = name,
//    id = id,
//    puuid = puuid,
//    summonerLevel = summonerLevel
//)