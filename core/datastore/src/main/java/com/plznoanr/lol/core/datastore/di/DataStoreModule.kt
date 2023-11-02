package com.plznoanr.lol.core.datastore.di

import android.content.Context
import com.plznoanr.lol.core.datastore.DataStoreManager
import com.plznoanr.lol.core.datastore.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferenceData(@ApplicationContext context: Context): SharedPreferenceManager =
        SharedPreferenceManager(context)

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

}