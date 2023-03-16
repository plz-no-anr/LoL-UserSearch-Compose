package com.plznoanr.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plznoanr.data.model.local.ProfileEntity
import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.data.model.local.SummonerEntity

@Database(
    entities = [
        SummonerEntity::class,
        SearchEntity::class,
        ProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}