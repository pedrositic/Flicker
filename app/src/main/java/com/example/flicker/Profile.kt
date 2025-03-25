package com.example.flicker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Profile : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño para el fragmento
        val rootView = inflater.inflate(R.layout.activity_profile, container, false)

        // Obtener SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("AppPreferences", AppCompatActivity.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        // Configuración de bordes
        ViewCompat.setOnApplyWindowInsetsListener(rootView.findViewById(R.id.mainContent)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar Switch de Dark Mode
        val switchDarkMode = rootView.findViewById<Switch>(R.id.switchDarkMode)
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

        val btnDiagrams = rootView.findViewById<ImageButton>(R.id.btnDiagrams)
        btnDiagrams.setOnClickListener {
            val intent = Intent(requireContext(), DiagramsActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }
}