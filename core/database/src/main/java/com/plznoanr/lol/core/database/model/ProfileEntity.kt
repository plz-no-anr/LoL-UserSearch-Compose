package com.plznoanr.lol.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plznoanr.lol.core.model.Profile

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