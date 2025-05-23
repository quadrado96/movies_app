package com.quadrado.movies_app.repository

import com.quadrado.movies_app.models.MovieResponse
import com.quadrado.movies_app.network.ApiClient
import com.quadrado.movies_app.network.MovieApiService

class MovieRepository {
    private val api = ApiClient.retrofit.create(MovieApiService::class.java)

    suspend fun getPopularMovies(): MovieResponse {
        return api.getPopularMovies()
    }
}
