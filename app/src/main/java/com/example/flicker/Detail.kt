package com.example.flicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.flicker.data.model.MovieItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Detail.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val titleTextView = view.findViewById<TextView>(R.id.movieTitle)
        val yearTextView = view.findViewById<TextView>(R.id.movieYear)
        val genreTextView = view.findViewById<TextView>(R.id.movieGenre)
        val descriptionTextView = view.findViewById<TextView>(R.id.movieDescription)
        val posterImageView = view.findViewById<ImageView>(R.id.moviePoster)

        movie?.let {
            titleTextView.text = it.title
            yearTextView.text = it.year.toString()
            genreTextView.text = it.genre
            descriptionTextView.text = it.description

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