package com.quadrado.movies_app.repository

import com.quadrado.movies_app.models.MovieDetails
import com.quadrado.movies_app.models.MovieResponse
import com.quadrado.movies_app.network.ApiClient
import com.quadrado.movies_app.network.MovieApiService
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovieRepository {
    private val api = ApiClient.retrofit.create(MovieApiService::class.java)

    suspend fun getPopularMovies(): MovieResponse {
        return api.getPopularMovies()
    }

    suspend fun getMoviesByGenre(genreId: Int): MovieResponse {
        return api.getMoviesByGenre(genreId)
    }

    suspend fun getTopRatedMovies(): MovieResponse {
        return api.getTopRatedMovies()
    }

    suspend fun getNowPlayingMovies(): MovieResponse {
        return api.getNowPlayingMovies()
    }

    suspend fun getUpcomingMovies(): MovieResponse {
        return api.getUpcomingMovies()
    }

    suspend fun searchMovies(query: String): MovieResponse {
        return api.searchMovies(query)
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return api.getMovieDetails(movieId)
    }

    suspend fun getRecentReleases(): MovieResponse {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return api.getRecentReleases(releaseDateLte = today)
    }

    suspend fun getMoviesByKeyword(keyword: String): MovieResponse {
        return api.getMoviesByKeyword(keyword)
    }

    suspend fun getMoviesByOriginalLanguage(languageCode: String): MovieResponse {
        return api.getMoviesByOriginalLanguage(languageCode)
    }


}
