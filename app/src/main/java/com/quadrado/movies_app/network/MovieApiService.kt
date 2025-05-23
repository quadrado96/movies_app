package com.quadrado.movies_app.network

import com.quadrado.movies_app.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int = 1
    ): MovieResponse
}
