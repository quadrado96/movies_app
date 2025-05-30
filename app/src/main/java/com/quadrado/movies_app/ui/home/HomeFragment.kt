package com.quadrado.movies_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.quadrado.movies_app.databinding.FragmentHomeBinding
import com.quadrado.movies_app.models.Category
import com.quadrado.movies_app.adapters.CategoryAdapter
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMovies(requireContext())

        val adapter = CategoryAdapter()
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = adapter

        adapter.setFavoriteClickListener { movie, position ->
            viewModel.favoriteMovie(requireContext(), movie) { favorited ->
                movie.favorited = favorited
                adapter.notifyItemChanged(position)
            }
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = adapter

        lifecycleScope.launch {
            val combine1 = combine(
                viewModel.movies,
                viewModel.topRatedMovies,
                viewModel.nowPlayingMovies,
                viewModel.actionMovies,
                viewModel.comedyMovies
            ) { moviesList, topRatedList, nowPlayingList, actionList, comedyList ->
                listOf(moviesList, topRatedList, nowPlayingList, actionList, comedyList)
            }

            val combine2 = combine(
                viewModel.horrorMovies,
                viewModel.romanceMovies,
                viewModel.fictionMovies,
                viewModel.adventureMovies,
                viewModel.animationMovies
            ) { horrorList, romanceList, fictionList, adventureList, animationList ->
                listOf(horrorList, romanceList, fictionList, adventureList, animationList)
            }

            combine(combine1, combine2) { list1, list2 ->
                listOf(
                    Category("Filmes populares", list1[0]),
                    Category("Mais Avaliados", list1[1]),
                    Category("Em cartaz", list1[2]),
                    Category("Ação", list1[3]),
                    Category("Comédia", list1[4]),
                    Category("Terror", list2[0]),
                    Category("Romance", list2[1]),
                    Category("Ficção", list2[2]),
                    Category("Aventura", list2[3]),
                    Category("Animação", list2[4])
                )
            }.collect { categorias ->
                adapter.setData(categorias)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
