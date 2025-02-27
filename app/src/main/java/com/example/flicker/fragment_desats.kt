package com.example.flicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flicker.movies.MovieAdapter
import com.example.flicker.movies.savedMovies

class fragment_desats : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_desats, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerViewMovies)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = MovieAdapter(savedMovies) { }
        return view
    }
}