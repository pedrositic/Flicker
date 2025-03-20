package com.example.flicker

import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.view.children
import com.example.flicker.data.RetrofitServiceFactory
import com.example.flicker.data.model.MovieItem
import com.example.flicker.movies.BaseMoviesFragment

class Saved : BaseMoviesFragment() {

    override fun getLayoutResId(): Int = R.layout.saved
    override fun getRecyclerViewId(): Int = R.id.recyclerViewMovies

    override suspend fun fetchMovies(): List<MovieItem> {
        val service = RetrofitServiceFactory.makeRetrofitService()
        return service.getFavourites()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMovies()

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