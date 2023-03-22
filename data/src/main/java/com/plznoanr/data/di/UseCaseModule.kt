package com.plznoanr.data.di

import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.key.GetKeyUseCase
import com.plznoanr.domain.usecase.profile.DeleteProfileUseCase
import com.plznoanr.domain.usecase.profile.GetProfileUseCase
import com.plznoanr.domain.usecase.profile.InsertProfileUseCase
import com.plznoanr.domain.usecase.search.DeleteAllSearchUseCase
import com.plznoanr.domain.usecase.search.DeleteSearchUseCase
import com.plznoanr.domain.usecase.search.GetSearchUseCase
import com.plznoanr.domain.usecase.search.InsertSearchUseCase
import com.plznoanr.domain.usecase.summoner.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetKeyUseCase(
        repository: AppRepository
    ) = GetKeyUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSearchUseCase(
        repository: AppRepository
    ) = GetSearchUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertSearchUseCase(
        repository: AppRepository
    ) = InsertSearchUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteSearchUseCase(
        repository: AppRepository
    ) = DeleteSearchUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteAllSearchUseCase(
        repository: AppRepository
    ) = DeleteAllSearchUseCase(repository)

    @Provides
    @Singleton
    fun provideGetProfileUseCase(
        repository: AppRepository
    ) = GetProfileUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertProfileUseCase(
        repository: AppRepository
    ) = InsertProfileUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteProfileUseCase(
        repository: AppRepository
    ) = DeleteProfileUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSummonerUseCase(
        repository: AppRepository
    ) = GetSummonerUseCase(repository)
    @Provides
    @Singleton
    fun provideRequestSummonerUseCase(
        repository: AppRepository
    ) = RequestSummonerUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertSummonerUseCase(
        repository: AppRepository
    ) = InsertSummonerUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteSummonerUseCase(
        repository: AppRepository
    ) = DeleteSummonerUseCase(repository)


    @Provides
    @Singleton
    fun provideDeleteAllSummonerUseCase(
        repository: AppRepository
    ) = DeleteAllSummonerUseCase(repository)


}
