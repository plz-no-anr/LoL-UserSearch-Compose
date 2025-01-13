package com.plznoanr.lol.core.data.di

import com.plznoanr.lol.core.data.repository.AppRepository
import com.plznoanr.lol.core.data.repository.DefaultAppRepository
import com.plznoanr.lol.core.data.repository.DefaultProfileRepository
import com.plznoanr.lol.core.data.repository.DefaultSearchRepository
import com.plznoanr.lol.core.data.repository.DefaultSettingRepository
import com.plznoanr.lol.core.data.repository.DefaultSummonerRepository
import com.plznoanr.lol.core.data.repository.ProfileRepository
import com.plznoanr.lol.core.data.repository.SearchRepository
import com.plznoanr.lol.core.data.repository.SettingRepository
import com.plznoanr.lol.core.data.repository.SummonerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAppRepository(
        defaultAppRepository: DefaultAppRepository
    ): AppRepository

    @Binds
    abstract fun bindProfileRepository(
        defaultProfileRepository: DefaultProfileRepository
    ) : ProfileRepository

    @Binds
    abstract fun bindSearchRepository(
        defaultSearchRepository: DefaultSearchRepository
    ) : SearchRepository

    @Binds
    abstract fun bindSummonerRepository(
        defaultSummonerRepository: DefaultSummonerRepository
    ) : SummonerRepository

    @Binds
    abstract fun bindSettingRepository(
        defaultSettingRepository: DefaultSettingRepository
    ) : SettingRepository

}