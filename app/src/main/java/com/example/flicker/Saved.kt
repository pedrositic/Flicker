package com.example.flicker

import android.os.Bundle
import android.view.View
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
    }
}