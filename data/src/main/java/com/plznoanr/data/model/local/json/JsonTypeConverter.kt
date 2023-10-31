package com.plznoanr.data.model.local.json

import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.datetime.LocalDateTime

class JsonTypeConverter {

    @TypeConverter
    fun fromRuneInfoList(value: List<RuneEntity.RuneInfo>?): String = Gson().toJson(value)

    @TypeConverter
    fun toRuneInfoList(value: String) = Gson().fromJson(value, Array<RuneEntity.RuneInfo>::class.java).toList()

    @TypeConverter
    fun fromSubRuneList(value: List<RuneEntity.RuneInfo.SubRune>?): String = Gson().toJson(value)

    @TypeConverter
    fun toSubRuneList(value: String) = Gson().fromJson(value, Array<RuneEntity.RuneInfo.SubRune>::class.java).toList()

    @TypeConverter
    fun fromLocalDataTime(value: LocalDateTime): String = Gson().toJson(value)

    @TypeConverter
    fun toLocalDataTime(value: String): LocalDateTime = Gson().fromJson(value, LocalDateTime::class.java)
}