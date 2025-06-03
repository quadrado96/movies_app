package com.quadrado.movies_app.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.quadrado.movies_app.R
import com.quadrado.movies_app.adapters.FavoriteMovieAdapter
import com.quadrado.movies_app.database.entities.FavoriteMovie
import com.quadrado.movies_app.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var movieAdapter: FavoriteMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        movieAdapter = FavoriteMovieAdapter()
        binding.rvResults.adapter = movieAdapter
        binding.rvResults.layoutManager = LinearLayoutManager(requireContext())

        observeSearchResults()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Digite o nome do filme..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.search(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun observeSearchResults() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchResults.collect { movies ->
                val favoriteMovies = movies.map {
                    FavoriteMovie(
                        movieId = it.id,
                        title = it.title,
                        posterPath = it.posterPath ?: ""
                    )
                }
                movieAdapter.setMovies(favoriteMovies)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        searchViewModel.clearResults()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
