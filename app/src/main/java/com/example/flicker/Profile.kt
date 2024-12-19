package com.example.flicker

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.widget.Switch
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Profile : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Obtener preferencias y aplicar el tema antes de crear la actividad
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Configuración de bordes
        //enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainContent)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar Switch de Dark Mode
        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)
        switchDarkMode.isChecked = isDarkMode

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Cambiar el tema según el estado del Switch
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            // Guardar el estado en SharedPreferences
            sharedPreferences.edit().putBoolean("isDarkMode", isChecked).apply()
        }
    }
}
