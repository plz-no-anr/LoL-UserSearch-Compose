package com.plznoanr.lol.core.data.di

import com.plznoanr.lol.core.data.repository.ProfileRepository
import com.plznoanr.lol.core.data.repository.ProfileRepositoryImpl
import com.plznoanr.lol.core.data.repository.local.profle.ProfileLocalDataSource
import com.plznoanr.lol.core.data.repository.local.profle.ProfileLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileModule {

    @Binds
    fun bindProfileLocalData(
        profileLocalDataSourceImpl: ProfileLocalDataSourceImpl
    ): ProfileLocalDataSource

    @Binds
    fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ) : ProfileRepository

}