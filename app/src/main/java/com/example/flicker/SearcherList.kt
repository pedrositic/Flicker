package com.example.flicker

import android.os.Bundle
import android.view.View
import com.example.flicker.data.model.MovieItem
import com.example.flicker.movies.BaseMoviesFragment

class Searcher : BaseMoviesFragment() {

    private var searchedMovies: List<MovieItem> = emptyList()
    override fun getLayoutResId(): Int = R.layout.fragment_searcher
    override fun getRecyclerViewId(): Int = R.id.recyclerViewMovies

    override suspend fun fetchMovies(): List<MovieItem> {
        return searchedMovies
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            searchedMovies = it.getSerializable("MOVIES_LIST") as? List<MovieItem> ?: emptyList()
        }
        loadMovies()
    }
}