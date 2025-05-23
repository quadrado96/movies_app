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
import com.quadrado.movies_app.models.Movie
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

        val adapter = CategoryAdapter()
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = adapter

        lifecycleScope.launch {
            combine(
                viewModel.actionMovies,
                viewModel.comedyMovies,
                viewModel.horrorMovies
            ) { actionList, comedyList, horrorList ->
                listOf(
                    Category("Ação", actionList),
                    Category("Comédia", comedyList),
                    Category("Terror", horrorList)
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
