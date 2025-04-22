package com.example.flicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    // LiveData para errores de validación
    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?> = _usernameError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _confirmPasswordError = MutableLiveData<String?>()
    val confirmPasswordError: LiveData<String?> = _confirmPasswordError

    /**
     * Valida el campo de nombre de usuario.
     */
    fun validateUsername(username: String) {
        when {
            username.isEmpty() -> _usernameError.value = "El nom d'usuari no pot estar buit."
            username.length > 50 -> _usernameError.value = "Màxim 50 caràcters."
            else -> _usernameError.value = null
        }
    }

    /**
     * Valida el campo de correo electrónico.
     */
    fun validateEmail(email: String) {
        when {
            email.isEmpty() -> _emailError.value = "El correu electrònic no pot estar buit."
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                _emailError.value = "Format de correu electrònic incorrecte."
            else -> _emailError.value = null
        }
    }

    /**
     * Valida el campo de contraseña.
     */
    fun validatePassword(password: String) {
        when {
            password.isEmpty() -> _passwordError.value = "La contrasenya no pot estar buida."
            password.length < 6 -> _passwordError.value = "Mínim 6 caràcters."
            !password.any { it.isUpperCase() } -> _passwordError.value = "Falta majúscula."
            !password.any { it.isDigit() } -> _passwordError.value = "Falta número."
            !password.any { !it.isLetterOrDigit() } ->
                _passwordError.value = "Falta caràcter especial."
            else -> _passwordError.value = null
        }
    }

    /**
     * Valida que las contraseñas coincidan.
     */
    fun validateConfirmPassword(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            _confirmPasswordError.value = "Les contrasenyes no coincideixen."
        } else {
            _confirmPasswordError.value = null
        }
    }
}