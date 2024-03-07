package com.plznoanr.lol.core.network.config

import com.plznoanr.lol.core.network.BuildConfig

class RiotApiConfiguration: ApiConfiguration {
    override val endPoint: String
        get() = BuildConfig.RIOT_ACCOUNT_BASE_URL

}