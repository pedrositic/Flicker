package com.example.flicker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo: ImageView = findViewById(R.id.splash_logo)
        val fondo: LinearLayout = findViewById(R.id.splash_layout)

        // Animación de zoom y rebote
        val animacionLogo = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        logo.startAnimation(animacionLogo)

        // Retraso para que la animación de entrada termine antes de desvanecer el fondo
        Handler(Looper.getMainLooper()).postDelayed({
            val animacionFondo = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            fondo.startAnimation(animacionFondo)

            // Cambiar a MainActivity después del desvanecimiento
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, Login::class.java))
                finish()
            }, 800) // Tiempo de la animación fade_out
        }, 2000) // Tiempo para que termine la animación inicial
    }
}