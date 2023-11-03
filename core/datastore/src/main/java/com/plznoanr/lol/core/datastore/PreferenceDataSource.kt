package com.plznoanr.lol.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferenceKey {
        val API_KEY = stringPreferencesKey("API_KEY")
        val INIT = booleanPreferencesKey("INIT_KEY")
    }

    val apiKeyFlow: Flow<String?> = dataStore.data.map {
        it[PreferenceKey.API_KEY]
    }

    val initFlow: Flow<Boolean?> = dataStore.data.map {
        it[PreferenceKey.INIT]
    }

    suspend fun storeApiKey(apiKey: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.API_KEY] = apiKey
        }
    }

    suspend fun storeInit(init: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.INIT] = init
        }
    }

    suspend fun clearApiKey() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKey.API_KEY)
        }
    }

}