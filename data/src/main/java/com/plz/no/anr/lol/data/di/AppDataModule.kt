package com.plz.no.anr.lol.data.di

import com.plz.no.anr.lol.data.db.dao.JsonDao
import com.plz.no.anr.lol.data.repository.AppRepositoryImpl
import com.plz.no.anr.lol.data.repository.local.DataStoreManager
import com.plz.no.anr.lol.data.repository.local.PreferenceDataSource
import com.plz.no.anr.lol.data.repository.local.app.AppLocalDataSource
import com.plz.no.anr.lol.data.repository.local.app.AppLocalDataSourceImpl
import com.plz.no.anr.lol.data.utils.JsonUtils
import com.plz.no.anr.lol.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppDataModule {

    @Provides
    fun provideAppLocalDataSource(
        jsonDao: JsonDao
    ): AppLocalDataSource = AppLocalDataSourceImpl(
        jsonDao
    )

    @Provides
    fun provideAppRepository(
        appLocalDataSource: AppLocalDataSource,
        dataStoreManager: DataStoreManager,
        jsonUtils: JsonUtils
    ) : AppRepository = AppRepositoryImpl(
        appLocalDataSource,
        dataStoreManager,
        jsonUtils
    )

}