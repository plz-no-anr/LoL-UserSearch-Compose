package com.plznoanr.lol.core.data.di

import com.plznoanr.lol.core.data.repository.AppRepository
import com.plznoanr.lol.core.data.repository.AppRepositoryImpl
import com.plznoanr.lol.core.data.repository.ProfileRepository
import com.plznoanr.lol.core.data.repository.ProfileRepositoryImpl
import com.plznoanr.lol.core.data.repository.SearchRepository
import com.plznoanr.lol.core.data.repository.SearchRepositoryImpl
import com.plznoanr.lol.core.data.repository.SettingRepository
import com.plznoanr.lol.core.data.repository.SettingRepositoryImpl
import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.data.repository.SummonerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindAppRepository(
        appRepositoryImpl: AppRepositoryImpl
    ): AppRepository

    @Binds
    fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ) : ProfileRepository

    @Binds
    fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ) : SearchRepository

    @Binds
    fun bindSummonerRepository(
        summonerRepositoryImpl: SummonerRepositoryImpl
    ) : SummonerRepository

    @Binds
    fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ) : SettingRepository

}