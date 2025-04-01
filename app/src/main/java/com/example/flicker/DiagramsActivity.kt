package com.example.flicker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flicker.data.SettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DiagramsActivity : AppCompatActivity() {

    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagrams)

        // Inicializar SettingsDataStore
        settingsDataStore = SettingsDataStore(this)

        // Cargar y mostrar los datos en el log
        loadAndLogData()
    }

    private fun loadAndLogData() {
        lifecycleScope.launch {
            // Obtener todas las categorías almacenadas
            val allCategories = getAllStoredCategories()

            // Recuperar y loguear los datos para cada categoría
            for (category in allCategories) {
                val clicks = settingsDataStore.getCategoryClicks(category).firstOrNull() ?: 0
                val filters = settingsDataStore.getFilterCount(category).firstOrNull() ?: 0

                Log.d("DiagramsActivity", "Categoría: $category | Clics: $clicks | Filtros: $filters")
            }
        }
    }

    private suspend fun getAllStoredCategories(): List<String> {
        // Leer todas las claves del DataStore
        val keys = settingsDataStore.dataStore.data.first().asMap().keys.mapNotNull { key ->
            val keyString = key.toString()
            if (keyString.startsWith("filter_count_")) {
                keyString.removePrefix("filter_count_") // Extraer el nombre de la categoría
            } else {
                null
            }
        }.distinct()

        return keys
    }
}