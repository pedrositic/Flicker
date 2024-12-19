package com.example.flicker

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flicker.movies.MovieAdapter
import com.example.flicker.movies.MovieProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class Movies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movies)

        var rv: RecyclerView = findViewById(R.id.recyclerView)
        var llistatreserves = MovieProvider.Movies

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = MovieAdapter(llistatreserves)

        val menu = findViewById<BottomNavigationView>(R.id.menu_bottom)
        menu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this, Movies::class.java)
                    startActivity(intent)
                }

                R.id.bottom_save -> {
                    val intent = Intent(this, Desats::class.java)
                    startActivity(intent)
                }

                R.id.bottom_profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}