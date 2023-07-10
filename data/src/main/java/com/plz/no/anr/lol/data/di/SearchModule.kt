package com.plz.no.anr.lol.data.di

import com.plz.no.anr.lol.data.db.dao.LolDao
import com.plz.no.anr.lol.data.repository.SearchRepositoryImpl
import com.plz.no.anr.lol.data.repository.local.search.SearchLocalDataSource
import com.plz.no.anr.lol.data.repository.local.search.SearchLocalDataSourceImpl
import com.plz.no.anr.lol.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {

    @Provides
    fun provideSearchLocalData(lolDao: LolDao): SearchLocalDataSource =
        SearchLocalDataSourceImpl(lolDao)

    @ViewModelScoped
    @Provides
    fun provideSearchRepository(
        localDataSource: SearchLocalDataSource,
    ) : SearchRepository = SearchRepositoryImpl(
        localDataSource,
    )

}