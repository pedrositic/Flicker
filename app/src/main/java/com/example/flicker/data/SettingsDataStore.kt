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

// Extensión para acceder al DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCES_NAME)

class SettingsDataStore(context: Context) {

    val dataStore = context.dataStore

    /**
     * Guarda el número de clics para una categoría específica.
     */
    suspend fun saveCategoryClick(category: String, clicks: Int) {
        val categoryKey = intPreferencesKey("clicks_$category")
        dataStore.edit { preferences ->
            preferences[categoryKey] = clicks
        }
    }

    /**
     * Recupera el número de clics para una categoría específica.
     */
    fun getCategoryClicks(category: String): Flow<Int> {
        val categoryKey = intPreferencesKey("clicks_$category")
        return dataStore.data.map { preferences ->
            preferences[categoryKey] ?: 0 // Devuelve 0 si no hay valor guardado
        }
    }

    /**
     * Incrementa el contador de clics para una categoría específica.
     */
    suspend fun incrementCategoryClick(category: String) {
        val categoryKey = intPreferencesKey("clicks_$category")
        dataStore.edit { preferences ->
            val currentClicks = preferences[categoryKey] ?: 0
            preferences[categoryKey] = currentClicks + 1
        }
    }

    // Clave para almacenar el número de filtros por categoría
    private fun getFilterKey(category: String): Preferences.Key<Int> {
        return intPreferencesKey("filter_count_$category")
    }

    /**
     * Guarda el número de filtros para una categoría específica.
     */
    suspend fun saveFilterCount(category: String, count: Int) {
        dataStore.edit { preferences ->
            preferences[getFilterKey(category)] = count
        }
    }

    /**
     * Recupera el número de filtros para una categoría específica.
     */
    fun getFilterCount(category: String): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[getFilterKey(category)] ?: 0 // Devuelve 0 si no hay valor guardado
        }
    }

    /**
     * Incrementa el contador de filtros para una categoría específica.
     */
    suspend fun incrementFilterCount(category: String) {
        dataStore.edit { preferences ->
            val currentCount = preferences[getFilterKey(category)] ?: 0
            preferences[getFilterKey(category)] = currentCount + 1
        }
    }

    /**
     * Inicializa datos ficticios para las categorías.
     */
    suspend fun initializeDummyData() {
        val categories = listOf("animation", "comedy", "action", "adventure")

        // Simular clics para cada categoría
        for (category in categories) {
            val clicks = (1..10).random() // Número aleatorio de clics entre 1 y 10
            saveCategoryClick(category, clicks)
        }

        // Simular filtros aplicados para cada categoría
        for (category in categories) {
            val filters = (1..5).random() // Número aleatorio de filtros entre 1 y 5
            saveFilterCount(category, filters)
        }
    }
}