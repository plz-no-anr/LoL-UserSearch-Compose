package com.plznoanr.data.di

import com.plznoanr.data.api.Endpoints
import com.plznoanr.data.api.UserSearchApi
import com.plznoanr.data.repository.remote.RemoteDataSource
import com.plznoanr.data.repository.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    @Singleton
    fun provideApiService(): UserSearchApi =
        Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserSearchApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteData(api: UserSearchApi): RemoteDataSource = RemoteDataSourceImpl(api)
}