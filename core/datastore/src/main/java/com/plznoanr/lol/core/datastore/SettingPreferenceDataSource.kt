package com.plznoanr.lol.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class SettingPreferenceDataSource @Inject constructor(
    @Named("setting") private val dataStore: DataStore<Preferences>
) {
    private object PreferenceKey {
        val API_KEY = stringPreferencesKey("API_KEY")
        val INIT = booleanPreferencesKey("INIT_KEY")
        val IS_DARK_THEME = booleanPreferencesKey("IS_DARK_THEME")
    }

    val apiKeyFlow: Flow<String?> = dataStore.data.map {
        it[PreferenceKey.API_KEY]
    }

    val initFlow: Flow<Boolean?> = dataStore.data.map {
        it[PreferenceKey.INIT]
    }

    val isDarkThemeFlow: Flow<Boolean?> = dataStore.data.map {
        it[PreferenceKey.IS_DARK_THEME]
    }

    suspend fun updateApiKey(apiKey: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.API_KEY] = apiKey
        }
    }

    suspend fun updateInit(init: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.INIT] = init
        }
    }

    suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.IS_DARK_THEME] = isDarkTheme
        }
    }

    suspend fun clearApiKey() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKey.API_KEY)
        }
    }

}