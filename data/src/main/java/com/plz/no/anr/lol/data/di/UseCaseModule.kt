package com.plz.no.anr.lol.data.di

import com.plz.no.anr.lol.domain.repository.AppRepository
import com.plz.no.anr.lol.domain.repository.ProfileRepository
import com.plz.no.anr.lol.domain.repository.SearchRepository
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.json.InitialLocalJsonUseCase
import com.plz.no.anr.lol.domain.usecase.key.DeleteKeyUseCase
import com.plz.no.anr.lol.domain.usecase.key.GetKeyUseCase
import com.plz.no.anr.lol.domain.usecase.key.InsertKeyUseCase
import com.plz.no.anr.lol.domain.usecase.profile.DeleteProfileUseCase
import com.plz.no.anr.lol.domain.usecase.profile.GetProfileUseCase
import com.plz.no.anr.lol.domain.usecase.profile.InsertProfileUseCase
import com.plz.no.anr.lol.domain.usecase.search.DeleteAllSearchUseCase
import com.plz.no.anr.lol.domain.usecase.search.DeleteSearchUseCase
import com.plz.no.anr.lol.domain.usecase.search.GetSearchUseCase
import com.plz.no.anr.lol.domain.usecase.search.InsertSearchUseCase
import com.plz.no.anr.lol.domain.usecase.spectator.RequestSpectatorUseCase
import com.plz.no.anr.lol.domain.usecase.summoner.*
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
        repository: SearchRepository
    ) = GetSearchUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideInsertSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: SearchRepository
    ) = InsertSearchUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: SearchRepository
    ) = DeleteSearchUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteAllSearchUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: SearchRepository
    ) = DeleteAllSearchUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideGetProfileUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: ProfileRepository
    ) = GetProfileUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideInsertProfileUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: ProfileRepository
    ) = InsertProfileUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteProfileUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: ProfileRepository
    ) = DeleteProfileUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideRequestSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: SummonerRepository
    ) = RequestSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideRequestSummonerListUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: SummonerRepository
    ) = ReadSummonerListUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideRefreshSummonerListUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: SummonerRepository
    ) = RefreshSummonerListUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideInsertSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: SummonerRepository
    ) = InsertSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: SummonerRepository
    ) = DeleteSummonerUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideDeleteAllSummonerUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: SummonerRepository
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
        repository: SummonerRepository
    ) = RequestSpectatorUseCase(coroutineDispatcher, repository)

    @Provides
    fun provideInitialLocalJsonUseCase(
        @CoroutineQualifiers.IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        repository: AppRepository
    ) = InitialLocalJsonUseCase(coroutineDispatcher, repository)

}
