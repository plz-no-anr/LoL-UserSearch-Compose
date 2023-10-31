package com.plznoanr.data.di

import com.plznoanr.data.repository.ProfileRepository
import com.plznoanr.data.repository.ProfileRepositoryImpl
import com.plznoanr.data.repository.local.profle.ProfileLocalDataSource
import com.plznoanr.data.repository.local.profle.ProfileLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProfileModule {

    @Binds
    fun bindProfileLocalData(
        profileLocalDataSourceImpl: ProfileLocalDataSourceImpl
    ): ProfileLocalDataSource

    @Singleton
    @Binds
    fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ) : ProfileRepository

}