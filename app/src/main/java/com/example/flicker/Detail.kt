package com.example.flicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.flicker.data.RetrofitServiceFactory
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
        val posterImageView = view.findViewById<android.widget.ImageView>(R.id.moviePoster)
        val ratingTextView = view.findViewById<TextView>(R.id.rating)
        val saveButton = view.findViewById<ImageButton>(R.id.buttonSave)
        val likeButton = view.findViewById<ImageButton>(R.id.buttonLike)

        movie?.let { movieItem ->
            titleTextView.text = movieItem.title
            yearTextView.text = movieItem.year.toString()
            genreTextView.text = movieItem.genre
            descriptionTextView.text = movieItem.description
            ratingTextView.text = movieItem.imdbRating ?: "N/A"

            Glide.with(requireContext())
                .load(movieItem.poster)
                .into(posterImageView)

            // Actualizar el ícono del botón "save"
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

            // Actualizar el ícono del botón "like"
            MovieUtils.updateLikeButtonIcon(likeButton, movieItem.liked)

            // Manejar el clic en el botón "like"
            likeButton.setOnClickListener {
                MovieUtils.toggleLikeMovie(
                    movieItem,
                    likeButton,
                    mainScope,
                    onError = { e ->
                        Toast.makeText(context, "Failed to update like status", Toast.LENGTH_SHORT).show()
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