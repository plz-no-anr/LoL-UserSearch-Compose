package com.plznoanr.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plznoanr.domain.model.Profile

@Entity(tableName = "Profile")
data class ProfileEntity(
    @PrimaryKey
    val name: String,
    val level: String,
    val icon: String
){
    fun toDomain() = Profile(
        name = name,
        level = level,
        icon = icon
    )
}
