package com.plz.no.anr.lol_usersearch_compose.di

import android.content.Context
import com.plznoanr.data.utils.JsonUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JsonModule {
    @Provides
    @Singleton
    fun provideJsonUtils(@ApplicationContext context: Context): JsonUtils = JsonUtils(context)
}