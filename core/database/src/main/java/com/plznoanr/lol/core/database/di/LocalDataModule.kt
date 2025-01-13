package com.plznoanr.lol.core.database.di

import com.plznoanr.lol.core.database.data.app.AppLocalDataSource
import com.plznoanr.lol.core.database.data.app.DefaultAppLocalDataSource
import com.plznoanr.lol.core.database.data.profle.DefaultProfileLocalDataSource
import com.plznoanr.lol.core.database.data.profle.ProfileLocalDataSource
import com.plznoanr.lol.core.database.data.search.DefaultSearchLocalDataSource
import com.plznoanr.lol.core.database.data.search.SearchLocalDataSource
import com.plznoanr.lol.core.database.data.summoner.DefaultSummonerLocalDataSource
import com.plznoanr.lol.core.database.data.summoner.SummonerLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataModule {
    @Binds
    abstract fun bindAppLocalDataSource(
        defaultAppLocalDataSource: DefaultAppLocalDataSource
    ): AppLocalDataSource

    @Binds
    abstract fun bindProfileLocalData(
        defaultProfileLocalDataSource: DefaultProfileLocalDataSource
    ): ProfileLocalDataSource

    @Binds
    abstract fun bindSearchLocalData(
        defaultSearchLocalDataSource: DefaultSearchLocalDataSource
    ): SearchLocalDataSource

    @Binds
    abstract fun bindSummonerLocalData(
        defaultSummonerLocalDataSource: DefaultSummonerLocalDataSource
    ): SummonerLocalDataSource

}