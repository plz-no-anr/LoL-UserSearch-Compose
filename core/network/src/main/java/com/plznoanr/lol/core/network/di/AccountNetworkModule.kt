package com.plznoanr.lol.core.network.di

import com.plznoanr.lol.core.network.config.ApiConfiguration
import com.plznoanr.lol.core.network.config.RiotApiConfiguration
import com.plznoanr.lol.core.network.di.qualifiers.EndPoint
import com.plznoanr.lol.core.network.di.qualifiers.EndPointType
import com.plznoanr.lol.core.network.retrofit.RetrofitGenerator
import com.plznoanr.lol.core.network.retrofit.RiotAccountApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountNetworkModule {

    @Provides
    @Singleton
    @Named("RiotApiConfiguration")
    fun provideRiotApiConfiguration(): ApiConfiguration = RiotApiConfiguration()

    @Provides
    @Singleton
    @EndPoint(EndPointType.Riot)
    fun provideRetrofit(
        @Named("RiotApiConfiguration") apiConfiguration: ApiConfiguration
    ): Retrofit = RetrofitGenerator(apiConfiguration).client

    @Provides
    @Singleton
    fun provideApiService(
        @EndPoint(EndPointType.Riot) retrofit: Retrofit
    ): RiotAccountApi = retrofit.create(RiotAccountApi::class.java)

}