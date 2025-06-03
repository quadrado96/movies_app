package com.quadrado.movies_app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quadrado.movies_app.models.Movie
import com.quadrado.movies_app.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchMovies(query)
                _searchResults.value = result.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clearResults() {
        _searchResults.value = emptyList()
    }

}
