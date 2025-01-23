package com.example.flicker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo: ImageView = findViewById(R.id.splash_logo)

        // Cargar la animación
        val animacion = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        logo.startAnimation(animacion)

        // Cambiar a MainActivity después de la animación
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Login::class.java))
            finish()
        }, 2500) // Duración de la animación
    }
}