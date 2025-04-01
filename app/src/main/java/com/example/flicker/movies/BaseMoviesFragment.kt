package com.example.flicker.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flicker.Detail
import com.example.flicker.R
import com.example.flicker.data.RetrofitServiceFactory
import com.example.flicker.data.SettingsDataStore
import com.example.flicker.data.model.MovieItem
import kotlinx.coroutines.launch

abstract class BaseMoviesFragment : Fragment() {

    protected lateinit var movieAdapter: MovieAdapter
    protected var allMovies: List<MovieItem> = emptyList() // Lista completa de películas
    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        setupRecyclerView(view)

        settingsDataStore = SettingsDataStore(requireContext())
        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(getRecyclerViewId())
        recyclerView.layoutManager = LinearLayoutManager(context)
        movieAdapter = MovieAdapter(
            emptyList(),
            { movie -> onMovieItemClick(movie) },
            { movie, position -> toggleSaveMovie(movie, position) },
            { movie, position -> toggleLikeMovie(movie, position) }
        )
        recyclerView.adapter = movieAdapter
    }

    protected abstract fun getLayoutResId(): Int
    protected abstract fun getRecyclerViewId(): Int
    protected abstract suspend fun fetchMovies(): List<MovieItem>

    private fun onMovieItemClick(movie: MovieItem) {
        Log.d("BaseMoviesFragment", "Clic en la película: ${movie.title}")

        lifecycleScope.launch {
            val categories = movie.genre.split(", ").map { it.trim() }
            for (category in categories) {
                settingsDataStore.incrementCategoryClick(category)
            }
        }

        openDetailFragment(movie)
    }

    private fun openDetailFragment(movie: MovieItem) {
        val fragment = Detail.newInstance(movie)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Alterna el estado de "guardado" de una película.
     */
    private fun toggleSaveMovie(movie: MovieItem, position: Int) {
        lifecycleScope.launch {
            try {
                val service = RetrofitServiceFactory.makeRetrofitService()

                // Alternar el estado de "saved"
                if (movie.saved == 1) {
                    service.removeFavourite(movie.id)
                    movie.saved = 0
                } else {
                    service.addFavourite(movie.id)
                    movie.saved = 1
                }

                // Notificar al adaptador sobre el cambio
                movieAdapter.notifyItemChanged(position)
            } catch (e: Exception) {
                Log.e("BaseMoviesFragment", "Error toggling save status", e)
                Toast.makeText(context, "Failed to update save status", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Alterna el estado de "like" de una película.
     */
    private fun toggleLikeMovie(movie: MovieItem, position: Int) {
        lifecycleScope.launch {
            try {
                val service = RetrofitServiceFactory.makeRetrofitService()

                // Alternar el estado de "liked"
                val newLiked = if (movie.liked == 1) 0 else 1
                service.toggleLike(movie.id) // Llamada al backend para alternar el estado

                // Actualizar el estado local
                movie.liked = newLiked

                // Notificar al adaptador sobre el cambio
                movieAdapter.notifyItemChanged(position)
            } catch (e: Exception) {
                Log.e("BaseMoviesFragment", "Error toggling like status", e)
                Toast.makeText(context, "Failed to update like status", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Carga las películas desde la fuente de datos.
     */
    protected open fun loadMovies() {
        lifecycleScope.launch {
            try {
                allMovies = fetchMovies() // Guardar la lista completa de películas
                movieAdapter.updateMovies(allMovies) // Mostrar todas las películas inicialmente
            } catch (e: Exception) {
                Log.e("BaseMoviesFragment", "Error fetching movies", e)
                Toast.makeText(context, "Failed to load movies", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Filtra las películas por categoría.
     * @param category La categoría seleccionada. Si es "Popular", muestra todas las películas.
     */
    protected fun filterMoviesByCategory(category: String) {
        val normalizedCategory = category.trim().lowercase() // Normalizar la categoría
        val filteredMovies = if (normalizedCategory == "popular") {
            allMovies // Mostrar todas las películas si se selecciona "Popular"
        } else {
            allMovies.filter { movie ->
                movie.genre.split(", ") // Dividir los géneros en una lista
                    .map { it.trim().lowercase() } // Normalizar géneros
                    .any { it == normalizedCategory } // Comparar géneros normalizados
            }
        }

        movieAdapter.updateMovies(filteredMovies) // Actualizar el RecyclerView con las películas filtradas

        lifecycleScope.launch {
            for (movie in filteredMovies) {
                settingsDataStore.incrementFilterCount(normalizedCategory)
                Log.d("BaseMoviesFragment", "Filtro aplicado: $normalizedCategory a la peliuclal: ${movie.title}")
            }
            Log.d("BaseMoviesFragment", "Filtro aplicado: $category")
        }
    }
}