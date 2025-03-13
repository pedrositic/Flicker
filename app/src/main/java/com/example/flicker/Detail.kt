package com.example.flicker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.flicker.data.model.MovieItem
import com.example.flicker.movies.MovieUtils
import kotlinx.coroutines.MainScope

class Detail : Fragment() {
    private var movie: MovieItem? = null
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getSerializable("movie") as? MovieItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_detail, container, false)

        val titleTextView = view.findViewById<TextView>(R.id.movieTitle)
        val yearTextView = view.findViewById<TextView>(R.id.movieYear)
        val genreTextView = view.findViewById<TextView>(R.id.movieGenre)
        val descriptionTextView = view.findViewById<TextView>(R.id.movieDescription)
        val posterImageView = view.findViewById<ImageView>(R.id.moviePoster)
        val ratingTextView = view.findViewById<TextView>(R.id.rating)
        val saveButton = view.findViewById<ImageButton>(R.id.buttonSave)

        movie?.let { movieItem ->
            titleTextView.text = movieItem.title
            yearTextView.text = movieItem.year.toString()
            genreTextView.text = movieItem.genre
            descriptionTextView.text = movieItem.description

            // Log del imdbRating para depuración
            Log.d("DetailFragment", "IMDb Rating: ${movieItem.imdbRating}")

            // Manejar valores nulos para el rating
            ratingTextView.text = if (movieItem.imdbRating != null) {
                movieItem.imdbRating
            } else {
                "N/A" // Valor predeterminado si el rating es null
            }

            Glide.with(requireContext())
                .load(movieItem.poster)
                .into(posterImageView)

            // Actualizar el ícono del botón "save" según el estado de `saved`
            MovieUtils.updateSaveButtonIcon(saveButton, movieItem.saved)

            // Manejar el clic en el botón "save"
            saveButton.setOnClickListener {
                MovieUtils.toggleSaveMovie(
                    movieItem,
                    saveButton,
                    mainScope,
                    onError = { e ->
                        Toast.makeText(context, "Failed to update save status", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
        return view
    }

    companion object {
        fun newInstance(movie: MovieItem) =
            Detail().apply {
                arguments = Bundle().apply {
                    putSerializable("movie", movie)
                }
            }
    }
}