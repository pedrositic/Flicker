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
import com.example.flicker.data.model.MovieItem
import kotlinx.coroutines.launch

abstract class BaseMoviesFragment : Fragment() {

    protected lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(getRecyclerViewId())
        recyclerView.layoutManager = LinearLayoutManager(context)
        movieAdapter = MovieAdapter(
            emptyList(),
            { movie -> onMovieItemClick(movie) },
            { movie, position -> toggleSaveMovie(movie, position) }
        )
        recyclerView.adapter = movieAdapter
    }

    protected abstract fun getLayoutResId(): Int
    protected abstract fun getRecyclerViewId(): Int
    protected abstract suspend fun fetchMovies(): List<MovieItem>

    private fun onMovieItemClick(movie: MovieItem) {
        Log.d("BaseMoviesFragment", "Clic en la película: ${movie.title} Con Rating: ${movie.imdbRating}")
        openDetailFragment(movie)
    }

    private fun openDetailFragment(movie: MovieItem) {
        val fragment = Detail.newInstance(movie)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun toggleSaveMovie(movie: MovieItem, position: Int) {
        lifecycleScope.launch {
            try {
                val service = RetrofitServiceFactory.makeRetrofitService()
                if (movie.saved == 1) {
                    service.removeFavourite(movie.id)
                    movie.saved = 0
                } else {
                    service.addFavourite(movie.id)
                    movie.saved = 1
                }
                movieAdapter.notifyItemChanged(position)
            } catch (e: Exception) {
                Log.e("BaseMoviesFragment", "Error toggling save status", e)
                Toast.makeText(context, "Failed to update save status", Toast.LENGTH_SHORT).show()
            }
        }
    }

    protected fun loadMovies() {
        lifecycleScope.launch {
            try {
                val movies = fetchMovies()
                movieAdapter.updateMovies(movies)
            } catch (e: Exception) {
                Log.e("BaseMoviesFragment", "Error fetching movies", e)
                Toast.makeText(context, "Failed to load movies", Toast.LENGTH_SHORT).show()
            }
        }
    }
}