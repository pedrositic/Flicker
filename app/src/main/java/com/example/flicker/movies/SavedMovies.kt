package com.example.flicker.movies

import com.example.flicker.data.model.MovieItem

val savedMovies: List<MovieItem> = listOf(
    MovieItem(
        id = 1.toString(),
        title = "Interstellar",
        year = 2014,
        genre = "Ciencia Ficción",
        description = "Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento de garantizar la supervivencia de la humanidad.",
        poster = "https://m.media-amazon.com/images/I/A1JVqNMI7UL._AC_UF894,1000_QL80_.jpg",
        saved = 1
    ),
    MovieItem(
        id = 2.toString(),
        title = "Inception",
        year = 2010,
        genre = "Ciencia Ficción/Thriller",
        description = "Un ladrón especializado en extraer secretos de la mente durante el sueño recibe una última misión: plantar una idea en la mente de su objetivo.",
        poster = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_FMjpg_UX1000_.jpg",
        saved = 1
    ),
    MovieItem(
        id = 3.toString(),
        title = "The Dark Knight",
        year = 2008,
        genre = "Acción/Crimen/Dirección",
        description = "Batman se enfrenta al Joker, un criminal que pone a Ciudad Gótica al borde del caos.",
        poster = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_FMjpg_UX1000_.jpg",
        saved = 1
    )
)