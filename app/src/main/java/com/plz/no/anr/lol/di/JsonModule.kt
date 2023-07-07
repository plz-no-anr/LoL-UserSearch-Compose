package com.plz.no.anr.lol.di

import android.content.Context
import com.plz.no.anr.lol.data.di.CoroutineQualifiers
import com.plz.no.anr.lol.data.utils.JsonUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object JsonModule {
    @Provides
    fun provideJsonUtils(
        @ApplicationContext context: Context,
        @CoroutineQualifiers.DefaultDispatcher coroutineDispatcher: CoroutineDispatcher
    ): JsonUtils = JsonUtils(context, coroutineDispatcher)
}