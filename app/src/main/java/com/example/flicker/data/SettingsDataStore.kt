package com.example.flicker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

// Acces a DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCES_NAME)

class SettingsDataStore(context: Context) {

    private val dataStore = context.dataStore

    // Clau per desar el número de clics a detail de pelicules
    private val CLICKS_KEY = intPreferencesKey("detail_item_clicks")

    /**
     * Guarda el nombre de clics a DataStore.
     */
    suspend fun saveClicksCount(clicks: Int) {
        dataStore.edit { preferences ->
            preferences[CLICKS_KEY] = clicks
        }
    }

    /**
     * Recupera el nombre de clicks de DataStore.
     */
    fun getClicksCount(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[CLICKS_KEY] ?: 0 // Retorna 0 si no hi han clicks
        }
    }
}