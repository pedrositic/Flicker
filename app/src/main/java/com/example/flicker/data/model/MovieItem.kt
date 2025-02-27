package com.example.flicker.data.model

import java.io.Serializable

data class MovieItem (
    val description: String,
    val genre: String,
    val id: String,
    val poster: String,
    val saved: Int,
    val title: String,
    val year: Int
) : Serializable