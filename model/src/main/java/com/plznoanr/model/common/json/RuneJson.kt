package com.plznoanr.model.common.json

@kotlinx.serialization.Serializable
data class RuneJson(
    val id: Long,
    val key: String,
    val icon: String,
    val name: String,
    val slots: List<RuneInfo>
) {
    @kotlinx.serialization.Serializable
    data class RuneInfo(
        val runes: List<SubRune>
    ) {
        @kotlinx.serialization.Serializable
        data class SubRune(
            val id: Long,
            val key: String,
            val icon: String,
            val name: String
        )
    }
}
