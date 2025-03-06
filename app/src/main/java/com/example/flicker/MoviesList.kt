package com.example.flicker

import android.os.Bundle
import android.view.View
import com.example.flicker.data.RetrofitServiceFactory
import com.example.flicker.data.model.MovieItem
import com.example.flicker.movies.BaseMoviesFragment

class MoviesList : BaseMoviesFragment() {

    override fun getLayoutResId(): Int = R.layout.movies_list
    override fun getRecyclerViewId(): Int = R.id.recyclerView

    override suspend fun fetchMovies(): List<MovieItem> {
        val service = RetrofitServiceFactory.makeRetrofitService()
        return service.listMovies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMovies()
    }
}