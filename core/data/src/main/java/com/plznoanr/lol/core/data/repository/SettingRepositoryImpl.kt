package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.datastore.PreferenceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val dataSource: PreferenceDataSource
): SettingRepository {
    override fun getIsDarkTheme(): Flow<Boolean> = dataSource.isDarkThemeFlow.map { it ?: false }

    override suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        dataSource.storeIsDarkTheme(isDarkTheme)
    }



}