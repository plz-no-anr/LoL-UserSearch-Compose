package com.plznoanr.lol.core.network.di

import com.plznoanr.lol.core.network.config.ApiConfiguration
import com.plznoanr.lol.core.network.config.LolApiConfiguration
import com.plznoanr.lol.core.network.di.qualifiers.EndPoint
import com.plznoanr.lol.core.network.di.qualifiers.EndPointType
import com.plznoanr.lol.core.network.retrofit.RetrofitGenerator
import com.plznoanr.lol.core.network.retrofit.UserSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LolNetworkModule {

    @Provides
    @Singleton
    @Named("LolApiConfiguration")
    fun provideLolApiConfiguration(): ApiConfiguration = LolApiConfiguration()

    @Provides
    @Singleton
    @EndPoint(EndPointType.Lol)
    fun provideRetrofit(
        @Named("LolApiConfiguration") apiConfiguration: ApiConfiguration
    ): Retrofit = RetrofitGenerator(apiConfiguration).client

    @Provides
    @Singleton
    fun provideApiService(
        @EndPoint(EndPointType.Lol) retrofit: Retrofit
    ): UserSearchApi = retrofit.create(UserSearchApi::class.java)

}