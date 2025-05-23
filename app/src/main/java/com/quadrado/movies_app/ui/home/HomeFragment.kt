package com.quadrado.movies_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.quadrado.movies_app.databinding.FragmentHomeBinding
import com.quadrado.movies_app.models.Movie
import com.quadrado.movies_app.models.Category
import com.quadrado.movies_app.adapters.CategoryAdapter

class HomeFragment : Fragment() {

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


        val filmes = listOf(
            Movie(id = 1, title = "Filme incrivel", posterPath = "https://via.placeholder.com/300x450", 9.0),
            Movie(id = 2, title = "Filme sensacional", posterPath = "https://via.placeholder.com/300x450",10.0),
            Movie(id = 3, title = "Filme horrivel pqp", posterPath = "https://via.placeholder.com/300x450",2.0),
            Movie(id = 4, title = "Filme mediano", posterPath = "https://via.placeholder.com/300x450",3.4),
            Movie(id = 5, title = "Filme dahorinha", posterPath = "https://via.placeholder.com/300x450",6.5),
            Movie(id = 6, title = "Filme sinistro", posterPath = "https://via.placeholder.com/300x450",7.7),

        )

        val categorias = listOf(
            Category("Ação", filmes),
            Category("Comédia", filmes),
            Category("Terror", filmes)
        )

        adapter.setData(categorias)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
