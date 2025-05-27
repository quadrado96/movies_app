package com.quadrado.movies_app.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quadrado.movies_app.models.Movie
import com.quadrado.movies_app.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _actionMovies = MutableStateFlow<List<Movie>>(emptyList())
    val actionMovies: StateFlow<List<Movie>> = _actionMovies

    private val _comedyMovies = MutableStateFlow<List<Movie>>(emptyList())
    val comedyMovies: StateFlow<List<Movie>> = _comedyMovies

    private val _horrorMovies = MutableStateFlow<List<Movie>>(emptyList())
    val horrorMovies: StateFlow<List<Movie>> = _horrorMovies

    private val _romanceMovies = MutableStateFlow<List<Movie>>(emptyList())
    val romanceMovies: StateFlow<List<Movie>> = _romanceMovies

    private val _adventureMovies = MutableStateFlow<List<Movie>>(emptyList())
    val adventureMovies: StateFlow<List<Movie>> = _adventureMovies

    private val _fictionMovies = MutableStateFlow<List<Movie>>(emptyList())
    val fictionMovies: StateFlow<List<Movie>> = _fictionMovies

    private val _animationMovies = MutableStateFlow<List<Movie>>(emptyList())
    val animationMovies: StateFlow<List<Movie>> = _animationMovies

    private val _topRatedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val topRatedMovies: StateFlow<List<Movie>> = _topRatedMovies

    private val _nowPlayingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Movie>> = _nowPlayingMovies

    private val _upcomingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovies: StateFlow<List<Movie>> = _upcomingMovies


    init {
        fetchMovies()
        fetchAllGenres()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                _topRatedMovies.value = repository.getTopRatedMovies().results
                _nowPlayingMovies.value = repository.getNowPlayingMovies().results
                _upcomingMovies.value = repository.getUpcomingMovies().results
                _movies.value = repository.getPopularMovies().results
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }

    private fun fetchAllGenres() {
        viewModelScope.launch {
            try {
                val action = repository.getMoviesByGenre(28).results
                val comedy = repository.getMoviesByGenre(35).results
                val horror = repository.getMoviesByGenre(27).results
                val romance = repository.getMoviesByGenre(10749).results
                val fiction = repository.getMoviesByGenre(878).results
                val animation = repository.getMoviesByGenre(16).results
                val adventure = repository.getMoviesByGenre(12).results

                _actionMovies.value = action
                _comedyMovies.value = comedy
                _horrorMovies.value = horror
                _romanceMovies.value = romance
                _fictionMovies.value = fiction
                _animationMovies.value = animation
                _adventureMovies.value = adventure

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
