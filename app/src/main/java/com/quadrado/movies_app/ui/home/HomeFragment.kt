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

        lifecycleScope.launch {
            val combine1 = combine(
                viewModel.movies,
                viewModel.topRatedMovies,
                viewModel.nowPlayingMovies,
                viewModel.actionMovies,
                viewModel.comedyMovies
            ) { list1, list2, list3, list4, list5 ->
                listOf(list1, list2, list3, list4, list5)
            }

            val combine2 = combine(
                viewModel.horrorMovies,
                viewModel.romanceMovies,
                viewModel.fictionMovies,
                viewModel.adventureMovies,
                viewModel.animationMovies
            ) { list1, list2, list3, list4, list5 ->
                listOf(list1, list2, list3, list4, list5)
            }

            val combine3 = combine(
                viewModel.dramaMovies,
                viewModel.documentaryMovies,
                viewModel.crimeMovies,
                viewModel.thrillerMovies,
                viewModel.fantasyMovies
            ) { list1, list2, list3, list4, list5 ->
                listOf(list1, list2, list3, list4, list5)
            }

            val combine4 = combine(
                viewModel.musicMovies,
                viewModel.warMovies,
                viewModel.westernMovies,
                viewModel.historyMovies,
                viewModel.familyMovies
            ) { list1, list2, list3, list4, list5 ->
                listOf(list1, list2, list3, list4, list5)
            }

            combine(combine1, combine2, combine3, combine4) { l1, l2, l3, l4 ->
                listOf(
                    Category("Filmes Populares", l1[0]),
                    Category("Mais Avaliados", l1[1]),
                    Category("Em Cartaz", l1[2]),
                    Category("Ação", l1[3]),
                    Category("Comédia", l1[4]),
                    Category("Terror", l2[0]),
                    Category("Romance", l2[1]),
                    Category("Ficção Científica", l2[2]),
                    Category("Aventura", l2[3]),
                    Category("Animação", l2[4]),
                    Category("Drama", l3[0]),
                    Category("Documentários", l3[1]),
                    Category("Crime", l3[2]),
                    Category("Suspense", l3[3]),
                    Category("Fantasia", l3[4]),
                    Category("Música", l4[0]),
                    Category("Guerra", l4[1]),
                    Category("Faroeste", l4[2]),
                    Category("História", l4[3]),
                    Category("Família", l4[4])
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
