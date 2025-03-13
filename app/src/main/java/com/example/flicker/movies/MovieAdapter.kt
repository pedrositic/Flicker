package com.example.flicker.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flicker.R
import com.bumptech.glide.Glide
import com.example.flicker.data.model.MovieItem

class MovieAdapter(
    var llistaMovies: List<MovieItem>,
    private val onItemClick: (MovieItem) -> Unit, // Función lambda para manejar clics
    private val onSaveClick: (MovieItem, Int) -> Unit // Función lambda para manejar clics en el botón de guardado
) : RecyclerView.Adapter<MovieAdapterHolder>() {

    fun updateMovies(newMovies: List<MovieItem>) {
        llistaMovies = newMovies // Actualiza la lista de películas
        notifyDataSetChanged()   // Notifica al adaptador que los datos han cambiado
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterHolder {
        var iteminflater = LayoutInflater.from(parent.context)
        var recycleritem = iteminflater.inflate(R.layout.item_layout, parent, false)
        return MovieAdapterHolder(recycleritem)
    }

    override fun getItemCount(): Int {
        return llistaMovies.size
    }

    override fun onBindViewHolder(holder: MovieAdapterHolder, position: Int) {
        val movie = llistaMovies[position]
        holder.Renderitzar(movie)

        // Manejo de clics: Llama a la función pasando la película seleccionada
        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }

        // Manejo de clics en el botón "save"
        holder.saved.setOnClickListener {
            onSaveClick(movie, holder.adapterPosition)
        }
    }
}

class MovieAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titol: TextView = itemView.findViewById(R.id.movieTitle)
    var year: TextView = itemView.findViewById(R.id.movieYear)
    var genre: TextView = itemView.findViewById(R.id.movieGenre)
    var foto: ImageView = itemView.findViewById(R.id.moviePoster)
    var saved: ImageView = itemView.findViewById(R.id.buttonSave)

    public fun Renderitzar(movie: MovieItem) {
        titol.text = movie.title
        year.text = movie.year.toString()
        genre.text = movie.genre

        if (movie.saved == 1) {
            saved.setImageResource(R.drawable.baseline_bookmark)
        } else {
            saved.setImageResource(R.drawable.baseline_bookmark_border)
        }

        Glide.with(itemView.context)
            .load(movie.poster)
            .into(foto)
    }
}