package com.plznoanr.data.di

import com.plznoanr.data.repository.AppRepository
import com.plznoanr.data.repository.AppRepositoryImpl
import com.plznoanr.data.repository.local.app.AppLocalDataSource
import com.plznoanr.data.repository.local.app.AppLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppDataModule {

    @Binds
    fun bindAppLocalDataSource(
        appLocalDataSourceImpl: AppLocalDataSourceImpl
    ): AppLocalDataSource

    @Binds
    @Singleton
    fun bindAppRepository(
        appRepositoryImpl: AppRepositoryImpl
    ): AppRepository

}