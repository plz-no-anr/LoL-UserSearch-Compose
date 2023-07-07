package com.plz.no.anr.lol.data.di

import com.plz.no.anr.lol.data.repository.AppRepositoryImpl
import com.plz.no.anr.lol.data.repository.local.LocalDataSource
import com.plz.no.anr.lol.data.repository.local.PreferenceDataSource
import com.plz.no.anr.lol.data.repository.remote.RemoteDataSource
import com.plz.no.anr.lol.data.utils.JsonUtils
import com.plz.no.anr.lol.domain.repository.AppRepository
import com.plz.no.anr.lol.data.di.CoroutineQualifiers
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