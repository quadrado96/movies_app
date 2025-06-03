package com.quadrado.movies_app.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quadrado.movies_app.database.Database
import com.quadrado.movies_app.database.entities.FavoriteMovie
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

    private val _dramaMovies = MutableStateFlow<List<Movie>>(emptyList())
    val dramaMovies: StateFlow<List<Movie>> = _dramaMovies

    private val _documentaryMovies = MutableStateFlow<List<Movie>>(emptyList())
    val documentaryMovies: StateFlow<List<Movie>> = _documentaryMovies

    private val _crimeMovies = MutableStateFlow<List<Movie>>(emptyList())
    val crimeMovies: StateFlow<List<Movie>> = _crimeMovies

    private val _thrillerMovies = MutableStateFlow<List<Movie>>(emptyList())
    val thrillerMovies: StateFlow<List<Movie>> = _thrillerMovies

    private val _fantasyMovies = MutableStateFlow<List<Movie>>(emptyList())
    val fantasyMovies: StateFlow<List<Movie>> = _fantasyMovies

    private val _musicMovies = MutableStateFlow<List<Movie>>(emptyList())
    val musicMovies: StateFlow<List<Movie>> = _musicMovies

    private val _warMovies = MutableStateFlow<List<Movie>>(emptyList())
    val warMovies: StateFlow<List<Movie>> = _warMovies

    private val _westernMovies = MutableStateFlow<List<Movie>>(emptyList())
    val westernMovies: StateFlow<List<Movie>> = _westernMovies

    private val _historyMovies = MutableStateFlow<List<Movie>>(emptyList())
    val historyMovies: StateFlow<List<Movie>> = _historyMovies

    private val _familyMovies = MutableStateFlow<List<Movie>>(emptyList())
    val familyMovies: StateFlow<List<Movie>> = _familyMovies

    init {
        fetchAllGenres()
    }

    fun fetchMovies(context: Context) {
        viewModelScope.launch {
            try {
                val topRated = repository.getTopRatedMovies().results
                val nowPlaying = repository.getNowPlayingMovies().results
                val upcoming = repository.getUpcomingMovies().results
                val popular = repository.getPopularMovies().results

                val db = Database.getInstance(context)
                val favorites = db.favoriteMovieDAO().getAllFavorites()

                fun List<Movie>.markFavorites(): List<Movie> {
                    return this.onEach { movie ->
                        movie.favorited = favorites.any { it.title == movie.title }
                    }
                }

                _topRatedMovies.value = topRated.markFavorites(favorites)
                _nowPlayingMovies.value = nowPlaying.markFavorites(favorites)
                _upcomingMovies.value = upcoming.markFavorites(favorites)
                _movies.value = popular.markFavorites(favorites)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchAllGenres() {
        viewModelScope.launch {
            try {
                _actionMovies.value = repository.getMoviesByGenre(28).results
                _comedyMovies.value = repository.getMoviesByGenre(35).results
                _horrorMovies.value = repository.getMoviesByGenre(27).results
                _romanceMovies.value = repository.getMoviesByGenre(10749).results
                _fictionMovies.value = repository.getMoviesByGenre(878).results
                _animationMovies.value = repository.getMoviesByGenre(16).results
                _adventureMovies.value = repository.getMoviesByGenre(12).results

                _dramaMovies.value = repository.getMoviesByGenre(18).results
                _documentaryMovies.value = repository.getMoviesByGenre(99).results
                _crimeMovies.value = repository.getMoviesByGenre(80).results
                _thrillerMovies.value = repository.getMoviesByGenre(53).results
                _fantasyMovies.value = repository.getMoviesByGenre(14).results
                _musicMovies.value = repository.getMoviesByGenre(10402).results
                _warMovies.value = repository.getMoviesByGenre(10752).results
                _westernMovies.value = repository.getMoviesByGenre(37).results
                _historyMovies.value = repository.getMoviesByGenre(36).results
                _familyMovies.value = repository.getMoviesByGenre(10751).results

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun favoriteMovie(context: Context, movie: Movie, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val db = Database.getInstance(context)
            val dao = db.favoriteMovieDAO()

            if (movie.favorited == true) {
                dao.removeFavorite(movie.toEntity())
                onComplete(false)
                Toast.makeText(context, "Removido dos favoritos :(", Toast.LENGTH_SHORT).show()
            } else {
                dao.addFavorite(movie.toEntity())
                onComplete(true)
                Toast.makeText(context, "Adicionado aos favoritos :D", Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun getFavoriteMoviesFromDB(context: Context): List<FavoriteMovie> {
        val db = Database.getInstance(context)
        val dao = db.favoriteMovieDAO()
        return dao.getAllFavorites()
    }

    private fun List<Movie>.markFavorites(favorites: List<FavoriteMovie>): List<Movie> {
        return this.onEach { movie ->
            movie.favorited = favorites.any { it.movieId == movie.id }
        }
    }
}
