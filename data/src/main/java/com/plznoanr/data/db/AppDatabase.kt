package com.plznoanr.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plznoanr.data.model.local.json.RuneEntity
import com.plznoanr.data.db.dao.JsonDao
import com.plznoanr.data.db.dao.LolDao
import com.plznoanr.data.model.local.json.JsonTypeConverter
import com.plznoanr.data.model.local.json.SpellEntity
import com.plznoanr.data.model.local.ProfileEntity
import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.data.model.local.json.ChampEntity
import com.plznoanr.data.model.local.SummonerEntity
import com.plznoanr.data.model.local.json.MapEntity

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