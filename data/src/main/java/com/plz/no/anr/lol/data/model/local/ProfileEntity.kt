package com.plz.no.anr.lol.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plz.no.anr.lol.domain.model.Profile

@Entity(tableName = "Profile")
data class ProfileEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val level: String,
    val icon: String
)
fun ProfileEntity.asDomain() = Profile(
    id = id,
    name = name,
    level = level,
    icon = icon
)