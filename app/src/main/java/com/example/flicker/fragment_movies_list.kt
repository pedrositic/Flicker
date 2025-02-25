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
import com.example.flicker.data.model.MovieItem

class fragment_movies_list : Fragment() {
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(context)

        // Configurar el adaptador con el manejador de clics
        movieAdapter = MovieAdapter(emptyList()) { movie ->
            openDetailFragment(movie)
        }
        rv.adapter = movieAdapter

        lifecycleScope.launch {
            try {
                val service = RetrofitServiceFactory.makeRetrofitService()
                val movies = service.listMovies()

                movieAdapter.updateMovies(movies)
            } catch (e: Exception) {
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
}
