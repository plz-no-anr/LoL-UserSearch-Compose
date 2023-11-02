package com.plznoanr.lol.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plznoanr.lol.core.database.dao.JsonDao
import com.plznoanr.lol.core.database.dao.LolDao
import com.plznoanr.lol.core.database.model.ProfileEntity
import com.plznoanr.lol.core.database.model.SearchEntity
import com.plznoanr.lol.core.database.model.SummonerEntity
import com.plznoanr.lol.core.database.model.json.ChampEntity
import com.plznoanr.lol.core.database.model.json.JsonTypeConverter
import com.plznoanr.lol.core.database.model.json.MapEntity
import com.plznoanr.lol.core.database.model.json.RuneEntity
import com.plznoanr.lol.core.database.model.json.SpellEntity

@Database(
    entities = [
        SummonerEntity::class,
        SearchEntity::class,
        ProfileEntity::class,
        MapEntity::class,
        ChampEntity::class,
        RuneEntity::class,
        SpellEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(JsonTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): LolDao
    abstract fun jsonDao(): JsonDao
}