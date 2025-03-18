package com.example.flicker.movies

import android.util.Log
import android.widget.ImageButton
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
                    service.removeFavourite(movie.id)
                    movie.saved = 0
                } else {
                    service.addFavourite(movie.id)
                    movie.saved = 1
                }
                updateSaveButtonIcon(button, movie.saved)
            } catch (e: Exception) {
                Log.e("MovieUtils", "Error toggling save status", e)
                onError(e)
            }
        }
    }

    /**
     * Actualiza el ícono del botón "save".
     */
    fun updateSaveButtonIcon(button: ImageView, saved: Int) {
        button.setImageResource(
            if (saved == 1) R.drawable.baseline_bookmark
            else R.drawable.baseline_bookmark_border
        )
    }

    /**
     * Alterna el estado de "like" de una película.
     */
    fun toggleLikeMovie(
        movie: MovieItem,
        button: ImageButton,
        scope: CoroutineScope,
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            try {
                val service = RetrofitServiceFactory.makeRetrofitService()

                // Alternar el estado de "liked"
                val newLiked = if (movie.liked == 1) 0 else 1
                service.toggleLike(movie.id) // Llamada al backend para alternar el estado
                movie.liked = newLiked

                // Actualizar el ícono del botón
                updateLikeButtonIcon(button, newLiked)
            } catch (e: Exception) {
                Log.e("MovieUtils", "Error toggling like status", e)
                onError(e)
            }
        }
    }

    /**
     * Actualiza el ícono del botón "like".
     */
    fun updateLikeButtonIcon(button: ImageButton, liked: Int) {
        button.setImageResource(
            if (liked == 1) R.drawable.baseline_like
            else R.drawable.baseline_like_border
        )
    }
}