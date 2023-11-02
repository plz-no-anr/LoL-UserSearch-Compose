package com.plznoanr.lol.core.model.common.json

@kotlinx.serialization.Serializable
data class MapJson(
    val type: String,
    val version: String,
    val data: Map<String, MapData>
) {
    @kotlinx.serialization.Serializable
    data class MapData(
        @kotlinx.serialization.SerialName("MapName")
        val mapName: String,
        @kotlinx.serialization.SerialName("MapId")
        val mapId: String,
        val image: Image
    ) {
        @kotlinx.serialization.Serializable
        data class Image(
            val full: String,
            val sprite: String,
            val group: String,
        )
    }

}
