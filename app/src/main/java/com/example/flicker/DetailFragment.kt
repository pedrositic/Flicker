package com.example.flicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.flicker.data.model.MovieItem
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val movie = arguments?.getSerializable("movie") as? MovieItem
        movie?.let {
            view.findViewById<TextView>(R.id.movieTitle).text = it.title
            view.findViewById<TextView>(R.id.movieYear).text = it.year
            view.findViewById<TextView>(R.id.movieDescription).text = it.description

            val imageView = view.findViewById<ImageView>(R.id.movieImage)
            Picasso.get().load(it.imageUrl).into(imageView) // Cargar imagen con Picasso
        }

        return view
    }
}
