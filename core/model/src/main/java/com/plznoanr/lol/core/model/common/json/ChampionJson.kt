package com.plznoanr.lol.core.model.common.json

@kotlinx.serialization.Serializable
data class ChampionJson(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, Champion>
) {
    @kotlinx.serialization.Serializable
    data class Champion(
        val id: String,
        val key: String,
        val name: String,
        val title: String,
        val image: ChampImage
    ) {
        @kotlinx.serialization.Serializable
        data class ChampImage(
            val full: String,
            val sprite: String,
            val group: String,
        )
    }
}
