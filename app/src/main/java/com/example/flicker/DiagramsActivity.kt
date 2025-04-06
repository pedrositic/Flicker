package com.example.flicker
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flicker.R
import com.example.flicker.data.SettingsDataStore
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DiagramsActivity : AppCompatActivity() {

    private lateinit var settingsDataStore: SettingsDataStore
    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagrams)

        // Inicializar SettingsDataStore
        settingsDataStore = SettingsDataStore(this)

        // Referenciar los gráficos
        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)

        // Cargar y mostrar los datos en el log
        lifecycleScope.launch {
            val categoryMetricsMap = loadCategoryMetrics()
            logCategoryMetrics(categoryMetricsMap)

            // Renderizar los diagramas
            setupBarChart(categoryMetricsMap)
            setupPieChart(categoryMetricsMap)
        }
    }

    /**
     * Configura y renderiza el gráfico de barras.
     */
    private fun setupBarChart(categoryMetricsMap: Map<String, Pair<Int, Int>>) {
        // Preparar los datos del gráfico
        val entriesClicks = mutableListOf<BarEntry>()
        val entriesFilters = mutableListOf<BarEntry>()
        val labels = mutableListOf<String>()

        // Iterar sobre las entradas del mapa con índice
        categoryMetricsMap.entries.forEachIndexed { index, entry ->
            val category = entry.key
            val metrics = entry.value

            entriesClicks.add(BarEntry(index.toFloat(), metrics.first.toFloat())) // Clics
            entriesFilters.add(BarEntry(index.toFloat(), metrics.second.toFloat())) // Filtros
            labels.add(category)
        }

        // Crear conjuntos de datos
        val dataSetFilters = BarDataSet(entriesFilters, "Filtros").apply {
            color = Color.GREEN
        }

        // Combinar los conjuntos de datos
        val barData = BarData(dataSetFilters)
        barChart.data = barData

        // Configurar el eje X
        barChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(labels) // Asignar etiquetas de categorías
            position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            setDrawGridLines(false)
        }

        // Configurar el diseño del gráfico
        barChart.apply {
            description.isEnabled = false // Desactivar la descripción
            legend.isEnabled = true // Mostrar leyenda
            axisLeft.axisMinimum = 0f // Valor mínimo del eje Y
            axisRight.isEnabled = false // Ocultar eje derecho
            setTouchEnabled(true) // Permitir interacción táctil
            setPinchZoom(false) // Desactivar zoom
            invalidate() // Actualizar el gráfico
        }
    }

    private fun setupPieChart(categoryMetricsMap: Map<String, Pair<Int, Int>>) {
        val pieEntries = mutableListOf<PieEntry>()

        // Generar datos: clics + filtros por categoría
        for ((category, metrics) in categoryMetricsMap) {
            val total = metrics.first + metrics.second
            if (total > 0) {
                pieEntries.add(PieEntry(total.toFloat(), category))
            }
        }

        val pieDataSet = PieDataSet(pieEntries, "Distribución total por categoría").apply {
            setColors(Color.RED, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.GREEN, Color.YELLOW)
            valueTextColor = Color.BLACK
            valueTextSize = 14f
        }

        val pieData = PieData(pieDataSet)
        pieChart.data = pieData

        pieChart.apply {
            description.isEnabled = false
            legend.isEnabled = true
            setUsePercentValues(true)
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
            invalidate() // Redibujar
        }
    }


    /**
     * Carga las métricas de todas las categorías.
     */
    private suspend fun loadCategoryMetrics(): Map<String, Pair<Int, Int>> {
        val allCategories = getAllStoredCategories()

        return allCategories.associateWith { category ->
            val clicks = settingsDataStore.getCategoryClicks(category).firstOrNull() ?: 0
            val filters = settingsDataStore.getFilterCount(category).firstOrNull() ?: 0
            Pair(clicks, filters)
        }
    }

    /**
     * Registra las métricas de las categorías en el log.
     */
    private fun logCategoryMetrics(categoryMetricsMap: Map<String, Pair<Int, Int>>) {
        for ((category, metrics) in categoryMetricsMap) {
            Log.d(
                "DiagramsActivity",
                "Categoría: $category | Clics: ${metrics.first} | Filtros: ${metrics.second}"
            )
        }
    }

    /**
     * Obtiene todas las categorías almacenadas.
     */
    private suspend fun getAllStoredCategories(): List<String> {
        val keys = settingsDataStore.dataStore.data.first().asMap().keys.mapNotNull { key ->
            val keyString = key.toString()
            if (keyString.startsWith("filter_count_")) {
                keyString.removePrefix("filter_count_")
            } else {
                null
            }
        }.distinct()

        return keys
    }
}