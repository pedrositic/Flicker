package com.example.flicker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction

class Footer : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del footer
        val rootView = inflater.inflate(R.layout.fragment_footer, container, false)

        // Obtener las referencias de los botones
        val ivHome = rootView.findViewById<ImageView>(R.id.ivHome)
        val ivDesat = rootView.findViewById<ImageView>(R.id.ivDesat)
        val ivProfile = rootView.findViewById<ImageView>(R.id.ivProfile)

        // Redirigir a la actividad de Movies al hacer clic en el ícono de Home
        ivHome.setOnClickListener {
            val intent = Intent(activity, Movies::class.java)
            startActivity(intent)
        }

        // Redirigir a la actividad de Desats al hacer clic en el ícono de Desat
        ivDesat.setOnClickListener {
            val intent = Intent(activity, Desats::class.java)
            startActivity(intent)
        }

        // Redirigir a la actividad de Profile al hacer clic en el ícono de Perfil
        ivProfile.setOnClickListener {
            val intent = Intent(activity, Profile::class.java)
            startActivity(intent)
        }

        return rootView
    }
}
