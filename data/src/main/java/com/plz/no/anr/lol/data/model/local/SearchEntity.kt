package com.plz.no.anr.lol.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plz.no.anr.lol.domain.model.Search

@Entity(tableName = "Search")
data class SearchEntity(
    @PrimaryKey
    val name: String,
    val date: String
)
fun SearchEntity.asDomain() = Search(
    name = name,
    date = date
)