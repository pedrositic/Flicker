package com.example.flicker

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.flicker.data.SettingsDataStore
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val registrar: TextView = findViewById(R.id.new_user)
        registrar.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val iniciar: TextView = findViewById(R.id.login_button)
        iniciar.setOnClickListener {
            val intent = Intent(this, Main::class.java)
            startActivity(intent)
        }

        // Inicializar SettingsDataStore
        settingsDataStore = SettingsDataStore(this)

        // Inicializar datos ficticios
        lifecycleScope.launch {
            settingsDataStore.initializeDummyData()
        }
    }
}