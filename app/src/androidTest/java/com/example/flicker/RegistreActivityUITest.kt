package com.example.flicker

import android.view.View
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistreActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Register::class.java)

    private lateinit var decorView: View

    @Before
    fun setUp() {
        Intents.init()
        activityRule.scenario.onActivity { activity ->
            decorView = activity.window.decorView
        }
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    // Test 1: Validar que se muestre un error si el campo de nombre de usuario está vacío
    @Test
    fun testNomUsuariBuit() {
        onView(withId(R.id.name_input)).perform(clearText())
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.name_input))
            .check(matches(hasErrorText("El nom d'usuari no pot estar buit.")))
    }

    // Test 2: Validar que se muestre un error si el campo de correo electrónico está vacío
    @Test
    fun testCorreuBuit() {
        onView(withId(R.id.email_input)).perform(clearText())
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.email_input))
            .check(matches(hasErrorText("El correu electrònic no pot estar buit.")))
    }

    // Test 3: Validar que se muestre un error si el campo de contraseña está vacío
    @Test
    fun testPasswordBuida() {
        onView(withId(R.id.password_input)).perform(clearText())
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("La contrasenya no pot estar buida.")))
    }

    // Test 4: Validar que se muestre un error si la contraseña tiene menos de 6 caracteres
    @Test
    fun testPasswordMenorDe6Caracters() {
        onView(withId(R.id.password_input)).perform(typeText("Pass1"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Falta caràcter especial.")))
    }

    // Test 5: Validar que se muestre un error si la contraseña no contiene mayúsculas
    @Test
    fun testPasswordSenseMajuscula() {
        onView(withId(R.id.password_input)).perform(typeText("password1!"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Falta majúscula.")))
    }

    // Test 6: Validar que se muestre un error si la contraseña no contiene números
    @Test
    fun testPasswordSenseNumero() {
        onView(withId(R.id.password_input)).perform(typeText("Password!"))
        onView(withId(R.id.password_check)).perform(typeText("Password!"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Falta número.")))
    }

    // Test 7: Validar que se muestre un error si la contraseña no contiene caracteres especiales
    @Test
    fun testPasswordSenseCaracterEspecial() {
        onView(withId(R.id.password_input)).perform(typeText("Password1"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Falta caràcter especial.")))
    }

    // Test 8: Validar que no se muestre ningún error si la contraseña es válida
    @Test
    fun testPasswordValida() {
        onView(withId(R.id.password_input)).perform(typeText("Password1!"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(withNoErrorText()))
    }

    // Matcher personalizado para verificar que no haya texto de error
    private fun withNoErrorText(): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("has no error text")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is EditText) return false
                return view.error == null
            }
        }
    }

    // Test 9: Validar que se muestre un error si las contraseñas no coinciden
    @Test
    fun testContrasenyesNoCoincidents() {
        onView(withId(R.id.password_input)).perform(typeText("Password1"))
        onView(withId(R.id.password_check)).perform(typeText("Password2"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_check))
            .check(matches(hasErrorText("Les contrasenyes no coincideixen.")))
    }

    // Test 10: Validar que el registro sea exitoso con datos válidos
    @Test
    fun testRegistreExitos() {
        // Ingresar datos válidos
        onView(withId(R.id.name_input)).perform(typeText("Usuari Test"))
        onView(withId(R.id.email_input)).perform(typeText("test@example.com"))
        onView(withId(R.id.password_input)).perform(typeText("Password1!"))
        onView(withId(R.id.password_check)).perform(typeText("Password1!"))
        onView(withId(R.id.login_button)).perform(click())

        // Verificar que se muestra el Toast "Usuari registrat correctament!"
        onView(withText("Usuari registrat correctament!"))
            .inRoot(RootMatchers.isPlatformPopup()) // Buscar en la ventana flotante
            .check(matches(isDisplayed()))

        // Verificar que se navega a la actividad Main
        intended(hasComponent(Main::class.java.name))
    }

    fun withToast(text: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("is toast with text '$text'")
            }

            override fun matchesSafely(view: View): Boolean {
                val context = view.context
                // Buscar el texto del Toast en las ventanas flotantes
                val windowToken = view.rootView.windowToken
                val decorView = context.getSystemService(android.content.Context.WINDOW_SERVICE)
                    ?.javaClass
                    ?.getMethod("getWindow", android.os.IBinder::class.java)
                    ?.invoke(context.getSystemService(android.content.Context.WINDOW_SERVICE), windowToken)

                return decorView?.toString()?.contains(text) ?: false
            }
        }
    }
}