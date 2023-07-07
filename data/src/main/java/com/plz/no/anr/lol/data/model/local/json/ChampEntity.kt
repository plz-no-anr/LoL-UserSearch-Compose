package com.plz.no.anr.lol.data.model.local.json

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Champ")
data class ChampEntity(
    val id: String,
    @PrimaryKey val key: String,
    val name: String,
    val title: String,
    @Embedded val image: Image
) {
    data class Image(
        val full: String,
        val sprite: String,
        val group: String,
    )
}
