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
import com.google.android.material.bottomnavigation.BottomNavigationView

class Movies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movies)

        // Inicialmente carga el fragmento principal
        if (savedInstanceState == null) {
            loadFragment(fragment_movies_list())
        }

        val menu = findViewById<BottomNavigationView>(R.id.menu_bottom)
        menu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_home -> {
                    loadFragment(fragment_movies_list())
                }

                R.id.bottom_save -> {
                   loadFragment(fragment_desats())
                }

                R.id.bottom_profile -> {
                    loadFragment(fragment_profile())
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}