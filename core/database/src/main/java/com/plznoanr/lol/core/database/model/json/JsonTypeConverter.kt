package com.plznoanr.lol.core.database.model.json

import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class JsonTypeConverter {
    private val json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    @OptIn(InternalSerializationApi::class)
    @TypeConverter
    fun fromRuneInfoList(value: List<RuneEntity.RuneInfo>?): String = json.encodeToString(
        Array<RuneEntity.RuneInfo>::class.serializer(),
        value?.toTypedArray() ?: emptyArray()
    )

    @TypeConverter
    fun toRuneInfoList(value: String): List<RuneEntity.RuneInfo>? = json.decodeFromString<List<RuneEntity.RuneInfo>?>(value)?.toList()

    @TypeConverter
    fun fromLocalDataTime(value: LocalDateTime): String = json.encodeToString(
        LocalDateTime.serializer(),
        value
    )

    @TypeConverter
    fun toLocalDataTime(value: String): LocalDateTime = json.decodeFromString(value)
}