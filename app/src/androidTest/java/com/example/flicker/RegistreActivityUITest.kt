package com.example.flicker

import android.view.View
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistreActivityUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(Register::class.java)

    private lateinit var decorView: android.view.View

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

    // Test 1: Validar que es mostri un error si el camp de nom d'usuari està buit
    @Test
    fun testNomUsuariBuit() {
        onView(withId(R.id.name_input)).perform(clearText())
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.name_input))
            .check(matches(hasErrorText("El nom d'usuari no pot estar buit.")))
    }

    // Validar que es mostri un error si el camp de correu electrònic està buit
    @Test
    fun testCorreuBuit() {
        onView(withId(R.id.email_input)).perform(clearText())
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.email_input))
            .check(matches(hasErrorText("El correu electrònic no pot estar buit.")))
    }

    @Test
    fun testPasswordBuida() {
        onView(withId(R.id.password_input)).perform(clearText())
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("La contrasenya no pot estar buida.")))
    }

    // Validar que se muestra un error si la contraseña tiene menos de 6 caracteres
    @Test
    fun testPasswordMenorDe6Caracters() {
        onView(withId(R.id.password_input)).perform(typeText("Pass1"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Mínim 6 caràcters.")))
    }

    // Validar que se muestra un error si la contraseña no contiene mayúsculas
    @Test
    fun testPasswordSenseMajuscula() {
        onView(withId(R.id.password_input)).perform(typeText("password1!"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Falta majúscula.")))
    }

    // Validar que es mostri un error si la contrasenya no compleix els requisits
    @Test
    fun testContrasenyaInvalida() {
        onView(withId(R.id.password_input)).perform(typeText("pass"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Mínim 6 caràcters, 1 majúscula, 1 minúscula i 1 número.")))
    }

    @Test
    fun testPasswordSenseNumero() {
        onView(withId(R.id.password_input)).perform(typeText("Password!"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Falta número.")))
    }

    // Validar que se muestra un error si la contraseña no contiene caracteres especiales
    @Test
    fun testPasswordSenseCaracterEspecial() {
        onView(withId(R.id.password_input)).perform(typeText("Password1"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Falta caràcter especial.")))
    }

    // Validar que no se muestra ningún error si la contraseña es válida
    @Test
    fun testPasswordValida() {
        onView(withId(R.id.password_input)).perform(typeText("Password1!"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(withNoErrorText()))
    }

    fun withNoErrorText(): Matcher<View> {
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


    // Validar que es mostri un error si les contrasenyes no coincideixen
    @Test
    fun testContrasenyesNoCoincidents() {
        onView(withId(R.id.password_input)).perform(typeText("Password1"))
        onView(withId(R.id.password_check)).perform(typeText("Password2"))
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.password_check))
            .check(matches(hasErrorText("Les contrasenyes no coincideixen.")))
    }

    // Validar que el registre sigui exitós amb dades vàlides
    @Test
    fun testRegistreExitos() {
        onView(withId(R.id.name_input)).perform(typeText("Usuari Test"))
        onView(withId(R.id.email_input)).perform(typeText("test@example.com"))
        onView(withId(R.id.password_input)).perform(typeText("Password1"))
        onView(withId(R.id.password_check)).perform(typeText("Password1"))
        onView(withId(R.id.login_button)).perform(click())

        // Verificar que es mostra el Toast "Usuari registrat correctament!"
        onView(withText("Usuari registrat correctament!"))
            .inRoot(withDecorView(not(decorView))) // Buscar fora del decorView principal
            .check(matches(isDisplayed()))

        // Verificar que es navega a l'activitat Main
        intended(hasComponent(Main::class.java.name))
    }
}