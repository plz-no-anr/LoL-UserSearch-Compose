package com.plznoanr.lol.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class SummonerPreferenceDataSource @Inject constructor(
    @Named("summoner") private val dataStore: DataStore<Preferences>
) {

    private object PreferenceKey {
        val BOOKMARK = stringSetPreferencesKey("BOOKMARK")
    }

    val bookmarkIdsFlow: Flow<Set<String>> = dataStore.data.map {
        it[PreferenceKey.BOOKMARK] ?: emptySet()
    }

    suspend fun updateBookmarkId(bookmarkIds: Set<String>) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.BOOKMARK] = bookmarkIds
        }
    }
}