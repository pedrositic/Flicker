package com.example.flicker

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
import com.example.flicker.data.RetrofitServiceFactory
import com.example.flicker.movies.MovieAdapter
import kotlinx.coroutines.launch

class fragment_movies_list : Fragment() {
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(context)

        // Inicializa el adaptador con una lista vacía
        movieAdapter = MovieAdapter(emptyList())
        rv.adapter = movieAdapter

        lifecycleScope.launch {
            try {
                // Obtiene la lista de películas desde la API
                val service = RetrofitServiceFactory.makeRetrofitService()
                val movies = service.listMovies()

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
}