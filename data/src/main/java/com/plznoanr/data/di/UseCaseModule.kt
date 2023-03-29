package com.plznoanr.data.di

import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.json.InitialLocalJsonUseCase
import com.plznoanr.domain.usecase.key.DeleteKeyUseCase
import com.plznoanr.domain.usecase.key.GetKeyUseCase
import com.plznoanr.domain.usecase.key.InsertKeyUseCase
import com.plznoanr.domain.usecase.profile.DeleteProfileUseCase
import com.plznoanr.domain.usecase.profile.GetProfileUseCase
import com.plznoanr.domain.usecase.profile.InsertProfileUseCase
import com.plznoanr.domain.usecase.search.DeleteAllSearchUseCase
import com.plznoanr.domain.usecase.search.DeleteSearchUseCase
import com.plznoanr.domain.usecase.search.GetSearchUseCase
import com.plznoanr.domain.usecase.search.InsertSearchUseCase
import com.plznoanr.domain.usecase.spectator.RequestSpectatorUseCase
import com.plznoanr.domain.usecase.summoner.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetKeyUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = GetKeyUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideGetSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = GetSearchUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideInsertSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InsertSearchUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideDeleteSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteSearchUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideDeleteAllSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteAllSearchUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideGetProfileUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = GetProfileUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideInsertProfileUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InsertProfileUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideDeleteProfileUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteProfileUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideGetSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = GetSummonerUseCase(coroutineDispatcher, repository)
    @Provides
    @Singleton
    fun provideRequestSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = RequestSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideInsertSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InsertSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideDeleteSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteSummonerUseCase(coroutineDispatcher, repository)


    @Provides
    @Singleton
    fun provideDeleteAllSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteAllSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideInsertKeyUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InsertKeyUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideDeleteKeyUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteKeyUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideRequestSpectatorUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = RequestSpectatorUseCase(coroutineDispatcher, repository)

    @Provides
    @Singleton
    fun provideInitialLocalJsonUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InitialLocalJsonUseCase(coroutineDispatcher, repository)

}
