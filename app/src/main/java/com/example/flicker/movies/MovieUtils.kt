package com.example.flicker.movies

import android.util.Log
import android.widget.ImageView
import com.example.flicker.R
import com.example.flicker.data.RetrofitServiceFactory
import com.example.flicker.data.model.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object MovieUtils {

    /**
     * Guarda o desmarca una película como favorita.
     */
    fun toggleSaveMovie(
        movie: MovieItem,
        button: ImageView,
        scope: CoroutineScope,
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            try {
                val service = RetrofitServiceFactory.makeRetrofitService()
                if (movie.saved == 1) {
                    // Si está guardada, eliminarla de favoritos
                    service.removeFavourite(movie.id)
                    movie.saved = 0
                } else {
                    // Si no está guardada, añadirla a favoritos
                    service.addFavourite(movie.id)
                    movie.saved = 1
                }

                // Actualizar el ícono del botón "save"
                updateSaveButtonIcon(button, movie.saved)
            } catch (e: Exception) {
                Log.e("MovieUtils", "Error toggling save status", e)
                onError(e)
            }
        }
    }

    /**
     * Actualiza el ícono del botón "save" según el estado de `saved`.
     */
    fun updateSaveButtonIcon(button: ImageView, saved: Int) {
        if (saved == 1) {
            button.setImageResource(R.drawable.baseline_bookmark) // Película guardada
        } else {
            button.setImageResource(R.drawable.baseline_bookmark_border) // Película no guardada
        }
    }
}