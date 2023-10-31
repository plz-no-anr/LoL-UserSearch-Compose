package com.plznoanr.data.di

import com.plznoanr.data.repository.SummonerRepository
import com.plznoanr.data.repository.SummonerRepositoryImpl
import com.plznoanr.data.repository.local.summoner.SummonerLocalDataSource
import com.plznoanr.data.repository.local.summoner.SummonerLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SummonerModule {

    @Binds
    fun bindSummonerLocalData(
        summonerLocalDataSourceImpl: SummonerLocalDataSourceImpl
    ): SummonerLocalDataSource

    @Binds
    @Singleton
    fun bindSummonerRepository(
        summonerRepositoryImpl: SummonerRepositoryImpl
    ) : SummonerRepository

}