package com.plznoanr.lol.core.data.di

import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.data.repository.SummonerRepositoryImpl
import com.plznoanr.lol.core.data.repository.local.summoner.SummonerLocalDataSource
import com.plznoanr.lol.core.data.repository.local.summoner.SummonerLocalDataSourceImpl
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
    fun bindSummonerRepository(
        summonerRepositoryImpl: SummonerRepositoryImpl
    ) : SummonerRepository

}