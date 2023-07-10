package com.plz.no.anr.lol.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plz.no.anr.lol.data.db.dao.JsonDao
import com.plz.no.anr.lol.data.db.dao.LolDao
import com.plz.no.anr.lol.data.model.local.ProfileEntity
import com.plz.no.anr.lol.data.model.local.SearchEntity
import com.plz.no.anr.lol.data.model.local.SummonerEntity
import com.plz.no.anr.lol.data.model.local.json.*

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