package com.plznoanr.data.di

import com.plznoanr.data.repository.AppRepositoryImpl
import com.plznoanr.data.repository.local.LocalDataSource
import com.plznoanr.data.repository.local.PreferenceDataSource
import com.plznoanr.data.repository.remote.RemoteDataSource
import com.plznoanr.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAppRepository(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        preferenceDataSource: PreferenceDataSource
    ) : AppRepository = AppRepositoryImpl(
        coroutineDispatcher,
        localDataSource,
        remoteDataSource,
        preferenceDataSource
    )
}