package com.example.flicker.data

import com.example.flicker.data.model.MovieItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

import okhttp3.OkHttpClient
import retrofit2.http.DELETE
import retrofit2.http.PUT
import java.security.cert.CertificateException
import javax.net.ssl.*

interface RetrofitService {

    // Endpoint para obtener todas las películas
    @GET("movies/")
    suspend fun listMovies(): List<MovieItem>

    // Endpoint para obtener detalles de una película por ID
    @GET("movies/{movie_id}")
    suspend fun readMovie(@Path("movie_id") movieId: String): MovieItem

    // Endpoint para buscar películas por título parcial
    @GET("movies/by_title/")
    suspend fun readMovieByTitle(@Query("title") title: String): List<MovieItem>

    // Endpoint para marcar/desmarcar "like" o "dislike"
    @PUT("movies/{movie_id}/toggle_like")
    suspend fun toggleLike(
        @Path("movie_id") movieId: String,
        @Query("like") like: Boolean
    ): Map<String, String>

    // Endpoint para añadir una película a favoritos
    @POST("favourites/add")
    suspend fun addFavourite(@Query("movie_id") movieId: String): Map<String, String>

    // Endpoint para eliminar una película de favoritos
    @DELETE("favourites/remove/{movie_id}")
    suspend fun removeFavourite(@Path("movie_id") movieId: String): Map<String, String>

    // Endpoint para obtener todas las películas favoritas
    @GET("favourites")
    suspend fun getFavourites(): List<MovieItem>
}

object RetrofitServiceFactory {
    val unsafeOkHttpClient = createUnsafeOkHttpClient()
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://18.210.63.191/")
            .client(unsafeOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}

fun createUnsafeOkHttpClient(): OkHttpClient {
    return try {
        // Crea un TrustManager que no valide los certificados
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })

        // Configura el SSLContext para usar el TrustManager personalizado
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        // Crea un SSLSocketFactory que use el SSLContext configurado
        val sslSocketFactory = sslContext.socketFactory

        // Construye el OkHttpClient con el SSLSocketFactory personalizado
        OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true } // Ignora la verificación del nombre de host
            .build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}