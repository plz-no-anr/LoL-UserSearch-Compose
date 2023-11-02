package com.plznoanr.lol.core.database.di

import android.content.Context
import androidx.room.Room
import com.plznoanr.lol.core.database.AppDatabase
import com.plznoanr.lol.core.database.dao.JsonDao
import com.plznoanr.lol.core.database.dao.LolDao
import com.plznoanr.lol.core.database.model.json.JsonTypeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "lol-app.db"
        ).build()

    @Provides
    fun provideAppDao(database: AppDatabase): LolDao =
        database.appDao()

    @Provides
    fun provideJsonDao(database: AppDatabase): JsonDao =
        database.jsonDao()

}