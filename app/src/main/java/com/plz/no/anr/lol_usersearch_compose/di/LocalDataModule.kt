package com.plz.no.anr.lol_usersearch_compose.di

import android.content.Context
import androidx.room.Room
import com.plznoanr.data.db.dao.AppDao
import com.plznoanr.data.db.AppDatabase
import com.plznoanr.data.db.dao.JsonDao
import com.plznoanr.data.repository.local.LocalDataSource
import com.plznoanr.data.repository.local.LocalDataSourceImpl
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
    fun provideAppDao(database: AppDatabase): AppDao = database.appDao()

    @Provides
    @Singleton
    fun provideJsonDao(database: AppDatabase): JsonDao = database.jsonDao()

    @Provides
    @Singleton
    fun provideLocalData(appDao: AppDao, jsonDao: JsonDao): LocalDataSource = LocalDataSourceImpl(appDao, jsonDao)
    @Provides
    @Singleton
    fun providePreferenceData(@ApplicationContext context: Context): PreferenceDataSource = PreferenceDataSource(context)

}