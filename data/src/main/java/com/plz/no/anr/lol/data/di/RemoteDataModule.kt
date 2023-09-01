package com.plz.no.anr.lol.data.di

import com.plz.no.anr.lol.data.api.RetrofitGenerator
import com.plz.no.anr.lol.data.api.UserSearchApi
import com.plz.no.anr.lol.data.api.config.ApiConfiguration
import com.plz.no.anr.lol.data.repository.remote.RemoteDataSource
import com.plz.no.anr.lol.data.repository.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteDataModule {

    @Provides
    @Singleton
    fun provideApiConfiguration() = ApiConfiguration()

    @Provides
    @Singleton
    fun provideRetrofit(
        apiConfiguration: ApiConfiguration
    ): Retrofit = RetrofitGenerator(apiConfiguration).client

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): UserSearchApi = retrofit.create(UserSearchApi::class.java)

    @Provides
    fun provideRemoteData(api: UserSearchApi): RemoteDataSource = RemoteDataSourceImpl(api)
}