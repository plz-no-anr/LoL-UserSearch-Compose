package com.plznoanr.lol.core.database.di

import com.plznoanr.lol.core.database.data.app.AppLocalDataSource
import com.plznoanr.lol.core.database.data.app.AppLocalDataSourceImpl
import com.plznoanr.lol.core.database.data.profle.ProfileLocalDataSource
import com.plznoanr.lol.core.database.data.profle.ProfileLocalDataSourceImpl
import com.plznoanr.lol.core.database.data.search.SearchLocalDataSource
import com.plznoanr.lol.core.database.data.search.SearchLocalDataSourceImpl
import com.plznoanr.lol.core.database.data.summoner.SummonerLocalDataSource
import com.plznoanr.lol.core.database.data.summoner.SummonerLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataModule {
    @Binds
    fun bindAppLocalDataSource(
        appLocalDataSourceImpl: AppLocalDataSourceImpl
    ): AppLocalDataSource

    @Binds
    fun bindProfileLocalData(
        profileLocalDataSourceImpl: ProfileLocalDataSourceImpl
    ): ProfileLocalDataSource

    @Binds
    fun bindSearchLocalData(
        searchLocalDataSourceImpl: SearchLocalDataSourceImpl
    ): SearchLocalDataSource

    @Binds
    fun bindSummonerLocalData(
        summonerLocalDataSourceImpl: SummonerLocalDataSourceImpl
    ): SummonerLocalDataSource

}