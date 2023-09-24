package com.plz.no.anr.lol.data.di

import com.plz.no.anr.lol.data.db.dao.LolDao
import com.plz.no.anr.lol.data.repository.SummonerRepositoryImpl
import com.plz.no.anr.lol.data.repository.local.DataStoreManager
import com.plz.no.anr.lol.data.repository.local.app.AppLocalDataSource
import com.plz.no.anr.lol.data.repository.local.search.SearchLocalDataSource
import com.plz.no.anr.lol.data.repository.local.summoner.SummonerLocalDataSource
import com.plz.no.anr.lol.data.repository.local.summoner.SummonerLocalDataSourceImpl
import com.plz.no.anr.lol.data.repository.remote.RemoteDataSource
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
internal object SummonerModule {

    @Provides
    fun provideSummonerLocalData(lolDao: LolDao): SummonerLocalDataSource =
        SummonerLocalDataSourceImpl(lolDao)

    @ViewModelScoped
    @Provides
    fun provideSummonerRepository(
        appLocalDataSource: AppLocalDataSource,
        summonerLocalDataSource: SummonerLocalDataSource,
        remoteDataSource: RemoteDataSource,
        dataStoreManager: DataStoreManager
    ) : SummonerRepository = SummonerRepositoryImpl(
        appLocalDataSource = appLocalDataSource,
        summonerLocalDataSource = summonerLocalDataSource,
        remoteDataSource = remoteDataSource,
        dataStoreManager = dataStoreManager
    )

}