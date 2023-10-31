package com.plznoanr.data.di

import com.plznoanr.data.api.RetrofitGenerator
import com.plznoanr.data.api.UserSearchApi
import com.plznoanr.data.api.config.ApiConfiguration
import com.plznoanr.data.repository.remote.RemoteDataSource
import com.plznoanr.data.repository.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    fun provideApiConfiguration() = ApiConfiguration()

    @Provides
    fun provideRetrofit(
        apiConfiguration: ApiConfiguration
    ): Retrofit = RetrofitGenerator(apiConfiguration).client

    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): UserSearchApi = retrofit.create(UserSearchApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteData(api: UserSearchApi): RemoteDataSource = RemoteDataSourceImpl(api)
}