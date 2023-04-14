package com.plznoanr.data.di

import com.plznoanr.data.repository.AppRepositoryImpl
import com.plznoanr.data.repository.local.LocalDataSource
import com.plznoanr.data.repository.local.PreferenceDataSource
import com.plznoanr.data.repository.remote.RemoteDataSource
import com.plznoanr.data.utils.JsonUtils
import com.plznoanr.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {
    @Provides
    fun provideAppRepository(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        preferenceDataSource: PreferenceDataSource,
        jsonUtils: JsonUtils
    ) : AppRepository = AppRepositoryImpl(
        coroutineDispatcher,
        localDataSource,
        remoteDataSource,
        preferenceDataSource,
        jsonUtils
    )
}