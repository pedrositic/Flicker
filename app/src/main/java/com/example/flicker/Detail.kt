package com.example.flicker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.flicker.data.model.MovieItem

class Detail : Fragment() {
    private var movie: MovieItem? = null

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

        movie?.let {
            titleTextView.text = it.title
            yearTextView.text = it.year.toString()
            genreTextView.text = it.genre
            descriptionTextView.text = it.description

            // Log del imdbRating para depuración
            Log.d("DetailFragment", "IMDb Rating: ${it.imdbRating}")

            // Manejar valores nulos para el rating
            ratingTextView.text = if (it.imdbRating != null) {
                it.imdbRating.toString()
            } else {
                "N/A" // Valor predeterminado si el rating es null
            }


            Glide.with(requireContext())
                .load(it.poster)
                .into(posterImageView)
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