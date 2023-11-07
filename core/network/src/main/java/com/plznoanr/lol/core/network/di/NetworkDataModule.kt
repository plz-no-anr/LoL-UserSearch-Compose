package com.plznoanr.lol.core.network.di

import com.plznoanr.lol.core.network.NetworkDataSource
import com.plznoanr.lol.core.network.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataModule {

    @Binds
    fun bindNetworkData(
        remoteDataSourceImpl: NetworkDataSourceImpl
    ): NetworkDataSource

}