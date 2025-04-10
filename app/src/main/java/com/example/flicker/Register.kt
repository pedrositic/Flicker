package com.example.flicker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.flicker.validations.RegisterViewModel

class Register : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Configurar el sistema de insets para ajustar el diseño
        setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // Referencias a los campos de entrada
        val nameInput = findViewById<EditText>(R.id.name_input)
        val emailInput = findViewById<EditText>(R.id.email_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val confirmPasswordInput = findViewById<EditText>(R.id.password_check)
        val registerButton = findViewById<Button>(R.id.login_button)

        // Observar errores del ViewModel
        viewModel.usernameError.observe(this) { error ->
            nameInput.error = error
        }

        viewModel.emailError.observe(this) { error ->
            emailInput.error = error
        }

        viewModel.passwordError.observe(this) { error ->
            passwordInput.error = error
        }

        viewModel.confirmPasswordError.observe(this) { error ->
            confirmPasswordInput.error = error
        }

        // Acción al hacer clic en el botón de registro
        registerButton.setOnClickListener {
            val username = nameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            // Validar campos
            viewModel.validateUsername(username)
            viewModel.validateEmail(email)
            viewModel.validatePassword(password)
            viewModel.validateConfirmPassword(password, confirmPassword)

            // Verificar si todos los campos son válidos
            if (viewModel.usernameError.value == null &&
                viewModel.emailError.value == null &&
                viewModel.passwordError.value == null &&
                viewModel.confirmPasswordError.value == null
            ) {
                Toast.makeText(this, "Usuari registrat correctament!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Main::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Corregeix els errors per continuar.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}