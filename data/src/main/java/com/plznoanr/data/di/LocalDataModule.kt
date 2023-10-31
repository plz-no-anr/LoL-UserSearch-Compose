package com.plznoanr.data.di

import android.content.Context
import androidx.room.Room
import com.plznoanr.data.db.AppDatabase
import com.plznoanr.data.db.dao.JsonDao
import com.plznoanr.data.db.dao.LolDao
import com.plznoanr.data.repository.local.DataStoreManager
import com.plznoanr.data.repository.local.PreferenceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "lol-app.db")
            .build()

    @Provides
    @Singleton
    fun provideAppDao(database: AppDatabase): LolDao = database.appDao()

    @Provides
    @Singleton
    fun provideJsonDao(database: AppDatabase): JsonDao = database.jsonDao()

    @Provides
    @Singleton
    fun providePreferenceData(@ApplicationContext context: Context): PreferenceDataSource =
        PreferenceDataSource(context)

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

}