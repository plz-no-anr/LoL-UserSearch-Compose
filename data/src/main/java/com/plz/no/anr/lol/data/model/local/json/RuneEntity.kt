package com.plz.no.anr.lol.data.model.local.json

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rune")
data class RuneEntity(
    val id: Long,
    @PrimaryKey val key: String,
    val icon: String,
    val name: String,
    val slots: List<RuneInfo>
) {
    data class RuneInfo(
        val runes: List<SubRune>
    ) {
        data class SubRune(
            val id: Long,
            val key: String,
            val icon: String,
            val name: String
        )
    }
}
