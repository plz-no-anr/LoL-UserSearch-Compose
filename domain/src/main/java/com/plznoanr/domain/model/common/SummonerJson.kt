package com.plznoanr.domain.model.common

@kotlinx.serialization.Serializable
data class SummonerJson(
    val type: String,
    val version: String,
    val data: Map<String, Spell>
) {
    @kotlinx.serialization.Serializable
    data class Spell(
        val id: String,
        val name: String,
        val description: String,
        val tooltip: String,
        val image: Image,
        val key: String
    ) {
        @kotlinx.serialization.Serializable
        data class Image(
            val full: String,
            val sprite: String,
            val group: String,
        )
    }

}
