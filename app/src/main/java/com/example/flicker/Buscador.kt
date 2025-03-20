package com.example.flicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flicker.data.RetrofitServiceFactory
import com.example.flicker.data.model.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Buscador : Fragment() {

    private lateinit var etBuscar: EditText
    private lateinit var btnBuscar: ImageButton
    private var listener: OnMoviesFetchedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buscador, container, false)

        etBuscar = view.findViewById(R.id.etSearch)
        btnBuscar = view.findViewById(R.id.btnSearch)

        // Acción al presionar el botón de búsqueda
        btnBuscar.setOnClickListener { buscarPeliculas() }

        // Acción al presionar "Intro" en el teclado
        etBuscar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                buscarPeliculas()
                true
            } else {
                false
            }
        }

        return view
    }

    private fun buscarPeliculas() {
        val titulo = etBuscar.text.toString().trim()
        if (titulo.isEmpty()) {
            Toast.makeText(context, "Ingresa un título", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = RetrofitServiceFactory.makeRetrofitService()
                val movies = apiService.readMovieByTitle(titulo)

                withContext(Dispatchers.Main) {
                    val searcherFragment = Searcher().apply {
                        arguments = Bundle().apply {
                            putSerializable("MOVIES_LIST", ArrayList(movies))
                        }
                    }

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, searcherFragment, "Searcher")
                        .addToBackStack(null)
                        .commit()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }

    // Interfaz para comunicar el resultado con fragment_movies_list
    interface OnMoviesFetchedListener {
        fun onMoviesFetched(movies: List<MovieItem>)
    }

    fun setOnMoviesFetchedListener(listener: OnMoviesFetchedListener) {
        this.listener = listener
    }
}