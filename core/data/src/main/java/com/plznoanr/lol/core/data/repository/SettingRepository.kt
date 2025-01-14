package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.datastore.SettingPreferenceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SettingRepository {
    fun getIsDarkTheme(): Flow<Boolean>

    suspend fun updateIsDarkTheme(isDarkTheme: Boolean)

}

class DefaultSettingRepository @Inject constructor(
    private val dataSource: SettingPreferenceDataSource
): SettingRepository {
    override fun getIsDarkTheme(): Flow<Boolean> = dataSource.isDarkThemeFlow.map { it ?: false }

    override suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        dataSource.updateIsDarkTheme(isDarkTheme)
    }

}