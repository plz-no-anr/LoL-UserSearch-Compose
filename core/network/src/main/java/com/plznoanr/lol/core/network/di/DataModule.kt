package com.plznoanr.lol.core.network.di

import com.plznoanr.lol.core.network.RemoteDataSource
import com.plznoanr.lol.core.network.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun provideRemoteData(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

}