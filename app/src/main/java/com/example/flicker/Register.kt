package com.example.flicker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class Register : AppCompatActivity() {

    // Inicialización del ViewModel usando by viewModels()
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Referencias a los campos de entrada
        val nameInput = findViewById<EditText>(R.id.name_input)
        val emailInput = findViewById<EditText>(R.id.email_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val confirmPasswordInput = findViewById<EditText>(R.id.password_check)
        val registerButton = findViewById<Button>(R.id.login_button)

        // Configurar listeners para validar campos en tiempo real
        nameInput.addTextChangedListener { text ->
            viewModel.validateUsername(text.toString())
        }

        emailInput.addTextChangedListener { text ->
            viewModel.validateEmail(text.toString())
        }

        passwordInput.addTextChangedListener { text ->
            viewModel.validatePassword(text.toString())
        }

        confirmPasswordInput.addTextChangedListener { text ->
            val password = passwordInput.text.toString()
            viewModel.validateConfirmPassword(password, text.toString())
        }

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
            validateAndProceed()
        }
    }

    // Valida todos los campos y procede con el registro si son válidos.
    private fun validateAndProceed() {
        val username = findViewById<EditText>(R.id.name_input).text.toString()
        val email = findViewById<EditText>(R.id.email_input).text.toString()
        val password = findViewById<EditText>(R.id.password_input).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.password_check).text.toString()

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