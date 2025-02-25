package com.example.flicker.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flicker.R
import com.example.flicker.data.model.MovieItem

class MovieAdapterDetail(movies: List<MovieItem>) :
    RecyclerView.Adapter<MovieAdapterHolder>() {
    private var movies: List<MovieItem>

    init {
        this.movies = movies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_detail, parent, false)
        return MovieAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapterHolder, position: Int) {
        holder.Renderitzar(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateMovies(newMovies: List<MovieItem>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }
}