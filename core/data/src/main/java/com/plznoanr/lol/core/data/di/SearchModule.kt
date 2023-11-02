package com.plznoanr.lol.core.data.di

import com.plznoanr.lol.core.data.repository.SearchRepository
import com.plznoanr.lol.core.data.repository.SearchRepositoryImpl
import com.plznoanr.lol.core.data.repository.local.search.SearchLocalDataSource
import com.plznoanr.lol.core.data.repository.local.search.SearchLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SearchModule {

    @Binds
    fun bindSearchLocalData(
        searchLocalDataSourceImpl: SearchLocalDataSourceImpl
    ): SearchLocalDataSource

    @Binds
    fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ) : SearchRepository

}