package com.plz.no.anr.lol.data.di

import com.plz.no.anr.lol.data.db.dao.LolDao
import com.plz.no.anr.lol.data.repository.ProfileRepositoryImpl
import com.plz.no.anr.lol.data.repository.local.profle.ProfileLocalDataSource
import com.plz.no.anr.lol.data.repository.local.profle.ProfileLocalDataSourceImpl
import com.plz.no.anr.lol.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ProfileModule {

    @Provides
    fun provideProfileLocalData(lolDao: LolDao): ProfileLocalDataSource =
        ProfileLocalDataSourceImpl(lolDao)

    @ViewModelScoped
    @Provides
    fun provideProfileRepository(
        localDataSource: ProfileLocalDataSource,
    ) : ProfileRepository = ProfileRepositoryImpl(
        localDataSource,
    )

}