package com.plz.no.anr.lol.data.di

import android.content.Context
import androidx.room.Room
import com.plz.no.anr.lol.data.db.AppDatabase
import com.plz.no.anr.lol.data.db.dao.AppDao
import com.plz.no.anr.lol.data.db.dao.JsonDao
import com.plz.no.anr.lol.data.repository.local.LocalDataSource
import com.plz.no.anr.lol.data.repository.local.LocalDataSourceImpl
import com.plz.no.anr.lol.data.repository.local.PreferenceDataSource
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
    fun provideAppDao(database: AppDatabase): AppDao = database.appDao()

    @Provides
    fun provideJsonDao(database: AppDatabase): JsonDao = database.jsonDao()

    @Provides
    fun provideLocalData(appDao: AppDao, jsonDao: JsonDao): LocalDataSource =
        LocalDataSourceImpl(appDao, jsonDao)
    @Provides
    fun providePreferenceData(@ApplicationContext context: Context): PreferenceDataSource =
        PreferenceDataSource(context)

}