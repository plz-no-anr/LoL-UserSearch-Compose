package com.plznoanr.data.di

import com.plznoanr.data.api.RetrofitGenerator
import com.plznoanr.data.api.UserSearchApi
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
internal object RemoteDataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = RetrofitGenerator().client
    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): UserSearchApi = retrofit.create(UserSearchApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteData(api: UserSearchApi): RemoteDataSource = RemoteDataSourceImpl(api)
}