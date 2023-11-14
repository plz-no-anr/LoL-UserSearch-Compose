package com.plznoanr.lol.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @AppDispatchers.Default
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @AppDispatchers.IO
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @AppDispatchers.Main
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @AppDispatchers.ApplicationScope
    @Provides
    fun provideApplicationScope(
        @AppDispatchers.Default dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(dispatcher + SupervisorJob())

}