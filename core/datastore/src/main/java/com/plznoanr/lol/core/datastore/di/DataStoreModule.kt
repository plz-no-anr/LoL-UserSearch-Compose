package com.plznoanr.lol.core.datastore.di

import android.content.Context
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
    fun provideSharedPreferenceData(@ApplicationContext context: Context): SharedPreferenceManager =
        SharedPreferenceManager(context)

}