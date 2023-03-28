package com.plznoanr.data.model.local.json

import androidx.room.TypeConverter
import com.google.gson.Gson

class JsonTypeConverter {
    @TypeConverter
    fun fromRuneInfoList(value: List<RuneEntity.RuneInfo>?): String = Gson().toJson(value)

    @TypeConverter
    fun toRuneInfoList(value: String) = Gson().fromJson(value, Array<RuneEntity.RuneInfo>::class.java).toList()

    @TypeConverter
    fun fromSubRuneList(value: List<RuneEntity.RuneInfo.SubRune>?): String = Gson().toJson(value)

    @TypeConverter
    fun toSubRuneList(value: String) = Gson().fromJson(value, Array<RuneEntity.RuneInfo.SubRune>::class.java).toList()
}