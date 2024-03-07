package com.plznoanr.lol.core.network.config

import com.plznoanr.lol.core.network.BuildConfig

class LolApiConfiguration : ApiConfiguration {
    override val endPoint: String
        get() = BuildConfig.RIOT_BASE_URL
}