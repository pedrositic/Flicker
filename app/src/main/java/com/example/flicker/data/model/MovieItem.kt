package com.example.flicker.data.model

import android.os.Parcelable
import java.io.Serializable

data class MovieItem(
    val description: String,
    val genre: String,
    val id: String,
    val poster: String,
    var saved: Int,
    val title: String,
    val year: Int,
    val imdbRating: String,  // Rating de IMDb
    var liked: Int      // Estado de "like" (true = like, false = dislike, null = no valorado)
) : Serializable