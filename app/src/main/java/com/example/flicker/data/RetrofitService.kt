package com.example.flicker.data

import com.example.flicker.data.model.MovieItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitService {

    @GET("/movies")
    suspend fun listMovies(
    )

    @POST("/movies")
    suspend fun addMovie(@Body movieItem: MovieItem)

    @POST("/movies/save/{movie_id}")
    suspend fun saveMovie(
        @Path("movie_id") movieId: String
    )

    @POST("/movies/unsave/{movie_id}")
    suspend fun unsaveMovie(
        @Path("movie_id") movieId: String
    )

    @GET("/movies/{movie_id}")
    suspend fun readMovie(
        @Path("movie_id") movieId: String
    )

    @GET("/movies/by_title/")
    suspend fun readMovieByTitle(
        @Query("movie_id") movieId: String
    )
}

object RetrofitServiceFactory {
    fun makeRetrofitService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://18.210.63.191/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}