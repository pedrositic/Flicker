package com.example.flicker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.flicker.data.RetrofitServiceFactory
import com.example.flicker.data.model.MovieItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Prova Retrofit
        val service = RetrofitServiceFactory.makeRetrofitService()
        lifecycleScope.launch {
            val movies = service.listMovies();
        }

        setContentView(R.layout.activity_main)

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