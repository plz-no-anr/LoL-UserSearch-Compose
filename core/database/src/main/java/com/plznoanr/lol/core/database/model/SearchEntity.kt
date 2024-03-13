package com.plznoanr.lol.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.model.Search
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "Search")
data class SearchEntity(
    @PrimaryKey
    val name: String,
    val tag: String,
    val date: LocalDateTime,
)
fun SearchEntity.asDomain() = Search(
    nickname = Nickname(name, tag),
    date = date,
)