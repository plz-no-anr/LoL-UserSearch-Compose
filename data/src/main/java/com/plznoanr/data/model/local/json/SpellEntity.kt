package com.plznoanr.data.model.local.json

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Spell")
data class SpellEntity(
    val id: String,
    val name: String,
    val description: String,
    val tooltip: String,
    @Embedded val image: Image,
    @PrimaryKey val key: String
) {
    data class Image(
        val full: String,
        val sprite: String,
        val group: String,
    )
}
