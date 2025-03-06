package com.example.flicker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flicker.data.RetrofitServiceFactory
import com.example.flicker.data.model.MovieItem
import com.example.flicker.movies.MovieAdapter
import kotlinx.coroutines.launch

class fragment_desats : Fragment() {
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_desats, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerViewMovies)
        rv.layoutManager = LinearLayoutManager(context)
        movieAdapter = MovieAdapter(emptyList(),
            { movie ->
                // Manejador para el clic en el item completo
                Log.d("FragmentMoviesList", "Clic en la película: ${movie.title} Con Rating: ${movie.imdbRating}")
                openDetailFragment(movie)
            },
            { movie ->
                // Manejador para el clic en el botón "save"
                Log.d("FragmentMoviesList", "Botón 'save' clicado para la película: ${movie.title}")
                toggleSaveMovie(movie)
            }
        )
        rv.adapter = movieAdapter

        lifecycleScope.launch {
            try {
                // Obtiene la lista de favoritos desde la API
                val service = RetrofitServiceFactory.makeRetrofitService()
                val movies = service.getFavourites()

                // Actualiza el adaptador con los datos obtenidos
                movieAdapter.updateMovies(movies) // Asume que `movies` tiene una propiedad `movies`
            } catch (e: Exception) {
                // Maneja errores (por ejemplo, muestra un mensaje al usuario)
                Log.e("FragmentMoviesList", "Error fetching movies", e)
                Toast.makeText(context, "Failed to load movies", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun openDetailFragment(movie: MovieItem) {
        val fragment = Detail.newInstance(movie)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun toggleSaveMovie(movie: MovieItem) {
        // Lógica para guardar/desmarcar la película como favorita
        lifecycleScope.launch {
            try {
                val service = RetrofitServiceFactory.makeRetrofitService()
                if (movie.saved == 1) {
                    // Si ya está guardada, eliminarla de favoritos
                    service.removeFavourite(movie.id)
                    movie.saved = 0
                } else {
                    // Si no está guardada, añadirla a favoritos
                    service.addFavourite(movie.id)
                    movie.saved = 1
                }

                // Actualizar la vista del RecyclerView
                movieAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.e("FragmentMoviesList", "Error toggling save status", e)
                Toast.makeText(context, "Failed to update save status", Toast.LENGTH_SHORT).show()
            }
        }
    }
}