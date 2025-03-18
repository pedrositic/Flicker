package com.example.flicker.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flicker.R
import com.example.flicker.data.model.MovieItem

class MovieAdapter(
    var llistaMovies: List<MovieItem>,
    private val onItemClick: (MovieItem) -> Unit,
    private val onSaveClick: (MovieItem, Int) -> Unit,
    private val onLikeClick: (MovieItem, Int) -> Unit
) : RecyclerView.Adapter<MovieAdapterHolder>() {

    fun updateMovies(newMovies: List<MovieItem>) {
        llistaMovies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterHolder {
        val itemInflater = LayoutInflater.from(parent.context)
        val recyclerItem = itemInflater.inflate(R.layout.item_layout, parent, false)
        return MovieAdapterHolder(recyclerItem)
    }

    override fun getItemCount(): Int = llistaMovies.size

    override fun onBindViewHolder(holder: MovieAdapterHolder, position: Int) {
        val movie = llistaMovies[position]
        holder.Renderitzar(movie)

        // Manejo de clics en el ítem principal
        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }

        // Manejo de clics en el botón "save"
        holder.saved.setOnClickListener {
            onSaveClick(movie, holder.adapterPosition)
        }

        // Manejo de clics en el botón "like"
        holder.liked.setOnClickListener {
            onLikeClick(movie, holder.adapterPosition)
        }
    }
}

class MovieAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titol: TextView = itemView.findViewById(R.id.movieTitle)
    var year: TextView = itemView.findViewById(R.id.movieYear)
    var genre: TextView = itemView.findViewById(R.id.movieGenre)
    var foto: ImageView = itemView.findViewById(R.id.moviePoster)
    var saved: ImageView = itemView.findViewById(R.id.buttonSave)
    var liked: ImageButton = itemView.findViewById(R.id.buttonLike)

    public fun Renderitzar(movie: MovieItem) {
        titol.text = movie.title
        year.text = movie.year.toString()
        genre.text = movie.genre

        // Estado del botón "save"
        if (movie.saved == 1) {
            saved.setImageResource(R.drawable.baseline_bookmark)
        } else {
            saved.setImageResource(R.drawable.baseline_bookmark_border)
        }

        // Estado del botón "like"
        if (movie.liked == 1) {
            liked.setImageResource(R.drawable.baseline_like)
        } else {
            liked.setImageResource(R.drawable.baseline_like_border)
        }

        Glide.with(itemView.context)
            .load(movie.poster)
            .into(foto)
    }
}