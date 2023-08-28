package com.plz.no.anr.lol.data.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DataStoreManager.DATASTORE_NAME,
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(
                context = context,
                sharedPreferencesName = PreferenceDataSource.LOL_APP,
                keysToMigrate = setOf( // 마이그레이션 하고자 하는 키 모음
                    PreferenceDataSource.API_KEY,
                    PreferenceDataSource.INIT_KEY
                )
            )
        )
    }
)

class DataStoreManager(
    private val context: Context
) {
    companion object {
        const val DATASTORE_NAME = "app_prefs"
        val API_KEY = stringPreferencesKey("API_KEY")
        val INIT_KEY = booleanPreferencesKey("INIT_KEY")
    }

    val apiKeyFlow: Flow<String?> = context.dataStore.data.map {
        it[API_KEY]
    }

    val initFlow: Flow<Boolean?> = context.dataStore.data.map {
        it[INIT_KEY]
    }

    suspend fun storeApiKey(apiKey: String) {
        context.dataStore.edit { preferences ->
            preferences[API_KEY] = apiKey
        }
    }

    suspend fun storeInit(init: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[INIT_KEY] = init
        }
    }

    suspend fun clearApiKey() {
        context.dataStore.edit { preferences ->
            preferences.remove(API_KEY)
        }
    }

}