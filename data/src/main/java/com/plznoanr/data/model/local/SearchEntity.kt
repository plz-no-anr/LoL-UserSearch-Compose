package com.plznoanr.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plznoanr.model.Search
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "Search")
data class SearchEntity(
    @PrimaryKey
    val name: String,
    val date: LocalDateTime
)
fun SearchEntity.asDomain() = Search(
    name = name,
    date = date
)