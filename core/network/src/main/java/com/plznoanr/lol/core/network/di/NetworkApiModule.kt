package com.plznoanr.lol.core.network.di

import com.plznoanr.lol.core.network.retrofit.RetrofitGenerator
import com.plznoanr.lol.core.network.retrofit.UserSearchApi
import com.plznoanr.lol.core.network.config.ApiConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkApiModule {

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

}