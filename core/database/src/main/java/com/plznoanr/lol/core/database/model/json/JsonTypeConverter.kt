package com.plznoanr.lol.core.database.model.json

import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.datetime.LocalDateTime

class JsonTypeConverter {

    @TypeConverter
    fun fromRuneInfoList(value: List<RuneEntity.RuneInfo>?): String = Gson().toJson(value)

    @TypeConverter
    fun toRuneInfoList(value: String): List<RuneEntity.RuneInfo> = Gson().fromJson(value, Array<RuneEntity.RuneInfo>::class.java).toList()

    @TypeConverter
    fun fromLocalDataTime(value: LocalDateTime): String = value.toString()

    @TypeConverter
    fun toLocalDataTime(value: String): LocalDateTime = LocalDateTime.parse(value)
}