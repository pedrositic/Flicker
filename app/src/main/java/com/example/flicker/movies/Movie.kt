package com.example.flicker.movies

data class Movie(
    val idMovie: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val description: String,
    val poster: String
)

class MovieProvider {

    companion object {
        @JvmStatic
        val Movies: List<Movie> = listOf(
            Movie(
                idMovie = 1,
                title = "Interstellar",
                year = 2014,
                genre = "Ciencia Ficción",
                description = "Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento de garantizar la supervivencia de la humanidad.",
                poster = "https://m.media-amazon.com/images/I/A1JVqNMI7UL._AC_UF894,1000_QL80_.jpg"
            ),
            Movie(
                idMovie = 2,
                title = "Inception",
                year = 2010,
                genre = "Ciencia Ficción/Thriller",
                description = "Un ladrón especializado en extraer secretos de la mente durante el sueño recibe una última misión: plantar una idea en la mente de su objetivo.",
                poster = "https://m.media-amazon.com/images/I/51d4D2fNeKL._AC_UF894,1000_QL80_.jpg"
            ),
            Movie(
                idMovie = 3,
                title = "The Dark Knight",
                year = 2008,
                genre = "Acción/Crimen/Dirección",
                description = "Batman se enfrenta al Joker, un criminal que pone a Ciudad Gótica al borde del caos.",
                poster = "https://m.media-amazon.com/images/I/51vFZtM1v9L._AC_UF894,1000_QL80_.jpg"
            ),
            Movie(
                idMovie = 4,
                title = "The Matrix",
                year = 1999,
                genre = "Ciencia Ficción/Acción",
                description = "Un hacker descubre la realidad virtual en la que vive y lucha contra las fuerzas que controlan su mundo.",
                poster = "https://m.media-amazon.com/images/I/41nbgSODzpL._AC_UF894,1000_QL80_.jpg"
            ),
            Movie(
                idMovie = 5,
                title = "Pulp Fiction",
                year = 1994,
                genre = "Crimen/Dramático",
                description = "Historias entrelazadas de personajes marginales en el submundo criminal de Los Ángeles.",
                poster = "https://m.media-amazon.com/images/I/41W-1lF4AiL._AC_UF894,1000_QL80_.jpg"
            ),
            Movie(
                idMovie = 6,
                title = "Fight Club",
                year = 1999,
                genre = "Drama",
                description = "Un hombre insatisfecho con su vida crea un club secreto de lucha para liberar sus tensiones.",
                poster = "https://m.media-amazon.com/images/I/41oWnPeL1LL._AC_UF894,1000_QL80_.jpg"
            ),
            Movie(
                idMovie = 7,
                title = "Forrest Gump",
                year = 1994,
                genre = "Drama/Romance",
                description = "La vida y las aventuras de un hombre con discapacidad mental que alcanza el éxito en muchos aspectos de la vida.",
                poster = "https://m.media-amazon.com/images/I/51l3HU0SD4L._AC_UF894,1000_QL80_.jpg"
            ),
            Movie(
                idMovie = 8,
                title = "The Shawshank Redemption",
                year = 1994,
                genre = "Drama",
                description = "La historia de la redención y la esperanza de Andy Dufresne, quien es condenado injustamente a cadena perpetua.",
                poster = "https://m.media-amazon.com/images/I/51NiGlapXlL._AC_UF894,1000_QL80_.jpg"
            ),
            Movie(
                idMovie = 9,
                title = "The Lion King",
                year = 1994,
                genre = "Animación/Familiar",
                description = "La clásica historia de Simba, un león que debe crecer y asumir el trono de su reino después de la muerte de su padre.",
                poster = "https://m.media-amazon.com/images/I/41FQ9hlbCbL._AC_UF894,1000_QL80_.jpg"
            ),
            Movie(
                idMovie = 10,
                title = "Star Wars: Episode IV - A New Hope",
                year = 1977,
                genre = "Ciencia Ficción/Acción",
                description = "La primera entrega de la saga Star Wars que narra la lucha de los rebeldes contra el Imperio Galáctico.",
                poster = "https://m.media-amazon.com/images/I/51nbP-7yf1L._AC_UF894,1000_QL80_.jpg"
            )
        )
    }
}

