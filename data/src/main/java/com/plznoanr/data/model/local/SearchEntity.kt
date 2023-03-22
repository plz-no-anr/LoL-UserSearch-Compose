package com.plznoanr.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plznoanr.domain.model.Search

@Entity(tableName = "Search")
data class SearchEntity(
    @PrimaryKey
    val name: String,
    val date: String
) {
    fun toDomain() = Search(
        name = name,
        date = date
    )
}