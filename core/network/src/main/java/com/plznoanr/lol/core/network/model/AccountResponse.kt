package com.plznoanr.lol.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    val puuid: String,
    val gameName: String,
    val tagLine: String
)