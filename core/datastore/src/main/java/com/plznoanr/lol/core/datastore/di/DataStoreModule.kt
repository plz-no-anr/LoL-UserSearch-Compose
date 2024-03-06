package com.plznoanr.lol.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.plznoanr.lol.core.datastore.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "setting_prefs",
    )

    private val Context.summonerDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "summoner_prefs",
    )

    @Provides
    @Singleton
    fun provideSharedPreferenceData(@ApplicationContext context: Context): SharedPreferenceManager =
        SharedPreferenceManager(context)

    @Provides
    @Singleton
    @Named("setting")
    fun provideSettingDataStore(@ApplicationContext context: Context) = context.settingDataStore

    @Provides
    @Singleton
    @Named("summoner")
    fun provideSummonerDataStore(@ApplicationContext context: Context) = context.summonerDataStore

}