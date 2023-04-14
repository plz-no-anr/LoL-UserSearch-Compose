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
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCaseModule {
    @Provides
    fun provideGetKeyUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = GetKeyUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideGetSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = GetSearchUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideInsertSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InsertSearchUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteSearchUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteAllSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteAllSearchUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideGetProfileUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = GetProfileUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideInsertProfileUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InsertProfileUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteProfileUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteProfileUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideRequestSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = RequestSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideRequestSummonerListUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = ReadSummonerListUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideRefreshSummonerListUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = RefreshSummonerListUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideInsertSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InsertSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteAllSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteAllSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideInsertKeyUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InsertKeyUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteKeyUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = DeleteKeyUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideRequestSpectatorUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = RequestSpectatorUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideInitialLocalJsonUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InitialLocalJsonUseCase(coroutineDispatcher, repository)

}
