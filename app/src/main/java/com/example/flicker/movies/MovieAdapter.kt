package com.example.flicker.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flicker.R
import com.bumptech.glide.Glide

class MovieAdapter(var llistaMovies: List<Movie>) : RecyclerView.Adapter<MovieAdapterHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterHolder {
        var iteminflater = LayoutInflater.from(parent.context)
        var recycleritem = iteminflater.inflate(R.layout.item_layout, parent, false)
        return MovieAdapterHolder(recycleritem)
    }

    override fun getItemCount(): Int {
        return llistaMovies.size
    }

    override fun onBindViewHolder(holder: MovieAdapterHolder, position: Int) {
        val movie = llistaMovies.get(position)
        holder.Renderitzar(movie)
    }
}


class MovieAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titol: TextView = itemView.findViewById(R.id.movieTitle)
    var year: TextView = itemView.findViewById(R.id.movieYear)
    var genre: TextView = itemView.findViewById(R.id.movieGenre)
    var foto: ImageView = itemView.findViewById(R.id.moviePoster)

    public fun Renderitzar(movie: Movie) {
        titol.text = movie.title
        year.text = movie.year.toString()
        genre.text = movie.genre

        Glide.with(itemView.context)
            .load(movie.poster)
            .into(foto)
    }
}