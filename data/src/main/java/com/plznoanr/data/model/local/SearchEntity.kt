package com.plznoanr.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plznoanr.domain.model.Search

@Entity(tableName = "Search")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val date: String
) {
    fun toDomain() = Search(
        uid = id,
        name = name,
        date = date
    )
}