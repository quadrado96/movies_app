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

    init {
        fetchPopularMovies()
        fetchAllGenres()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies()
                _movies.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }

    private fun fetchAllGenres() {
        viewModelScope.launch {
            try {
                val action = repository.getMoviesByGenre(28).results
                action.forEach { movie ->
                    Log.d("HomeViewModel", "Ação: ${movie.title} | Nota: ${movie.voteAverage}")
                }

                val comedy = repository.getMoviesByGenre(35).results
                comedy.forEach { movie ->
                    Log.d("HomeViewModel", "Comédia: ${movie.title} | Nota: ${movie.voteAverage}")
                }

                val horror = repository.getMoviesByGenre(27).results
                horror.forEach { movie ->
                    Log.d("HomeViewModel", "Terror: ${movie.title} | Nota: ${movie.voteAverage}")
                }

                Log.d("HomeViewModel", "Ação: ${action.size}")
                Log.d("HomeViewModel", "Comédia: ${comedy.size}")
                Log.d("HomeViewModel", "Terror: ${horror.size}")

                _actionMovies.value = action
                _comedyMovies.value = comedy
                _horrorMovies.value = horror

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
