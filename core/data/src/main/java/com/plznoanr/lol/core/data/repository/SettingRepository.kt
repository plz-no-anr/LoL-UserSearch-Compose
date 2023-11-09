package com.plznoanr.lol.core.data.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getIsDarkTheme(): Flow<Boolean>

    suspend fun updateIsDarkTheme(isDarkTheme: Boolean)

}