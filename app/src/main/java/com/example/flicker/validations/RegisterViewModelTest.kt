package com.example.flicker.validations

import androidx.arch.core.executor.testing.InstantTaskExecutorExtension
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorExtension()

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        viewModel = RegisterViewModel()
    }

    // Validación del campo "Nom d'Usuari"
    @Test
    fun testNomUsuariBuit() {
        viewModel.validateUsername("")
        assertEquals("El nom d'usuari no pot estar buit.", viewModel.usernameError.value)
    }

    @Test
    fun testNomUsuariMassaLlarg() {
        viewModel.validateUsername("a".repeat(51))
        assertEquals("Màxim 50 caràcters.", viewModel.usernameError.value)
    }

    @Test
    fun testNomUsuariAmbEspais() {
        viewModel.validateUsername("Paco Lluis")
        assertNull(viewModel.usernameError.value)
    }

    // Validación del campo "Correu Electrònic"
    @Test
    fun testCorreuBuit() {
        viewModel.validateEmail("")
        assertEquals("El correu electrònic no pot estar buit.", viewModel.emailError.value)
    }

    @Test
    fun testCorreuSenseArroba() {
        viewModel.validateEmail("usuariexemple.com")
        assertEquals("Format de correu electrònic incorrecte.", viewModel.emailError.value)
    }

    @Test
    fun testCorreuValid() {
        viewModel.validateEmail("usuari@exemple.com")
        assertNull(viewModel.emailError.value)
    }

    // Validación del campo "Contrasenya"
    @Test
    fun testContrasenyaBuida() {
        viewModel.validatePassword("")
        assertEquals("La contrasenya no pot estar buida.", viewModel.passwordError.value)
    }

    @Test
    fun testContrasenyaMassaCurta() {
        viewModel.validatePassword("Pass1")
        assertEquals("Mínim 6 caràcters.", viewModel.passwordError.value)
    }

    @Test
    fun testContrasenyaValida() {
        viewModel.validatePassword("ValidPass123!")
        assertNull(viewModel.passwordError.value)
    }

    // Validación de coincidencia de contraseñas
    @Test
    fun testContrasenyesNoCoincidents() {
        viewModel.validateConfirmPassword("123456", "654321")
        assertEquals("Les contrasenyes no coincideixen.", viewModel.confirmPasswordError.value)
    }

    @Test
    fun testContrasenyesCoincidents() {
        viewModel.validateConfirmPassword("ValidPass123!", "ValidPass123!")
        assertNull(viewModel.confirmPasswordError.value)
    }
}