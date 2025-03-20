package com.example.flicker

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.example.flicker.data.RetrofitServiceFactory
import com.example.flicker.data.model.MovieItem
import com.example.flicker.movies.BaseMoviesFragment
import kotlinx.coroutines.launch

class MoviesList : BaseMoviesFragment() {

    override fun getLayoutResId(): Int = R.layout.movies_list
    override fun getRecyclerViewId(): Int = R.id.recyclerView

    override suspend fun fetchMovies(): List<MovieItem> {
        val service = RetrofitServiceFactory.makeRetrofitService()
        return service.listMovies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar las películas inicialmente
        loadMovies()

        // Configurar listeners para los botones de categorías
        setupCategoryFilters(view)
    }

    private fun setupCategoryFilters(view: View) {
        val filters = view.findViewById<HorizontalScrollView>(R.id.filters)
        val buttons = (filters.getChildAt(0) as LinearLayout).children

        for (button in buttons) {
            button.setOnClickListener {
                val category = it.tag.toString() // Obtener la categoría del botón
                filterMoviesByCategory(category) // Usar el metodo del BaseMoviesFragment
            }
        }
    }
}