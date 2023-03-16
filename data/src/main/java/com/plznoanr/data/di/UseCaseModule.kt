package com.plznoanr.data.di

import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.key.GetKeyUseCase
import com.plznoanr.domain.usecase.search.DeleteAllSearchUseCase
import com.plznoanr.domain.usecase.search.DeleteSearchUseCase
import com.plznoanr.domain.usecase.search.GetSearchUseCase
import com.plznoanr.domain.usecase.search.InsertSearchUseCase
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


}
