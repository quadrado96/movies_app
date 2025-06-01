package com.quadrado.movies_app.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.quadrado.movies_app.FavoriteMoviesActivity
import com.quadrado.movies_app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupListeners()

        return root
    }

    private fun setupListeners() {
        binding.frmUser.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "abrir perfil",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnFavoritos.setOnClickListener {
            val intent = Intent(requireContext(), FavoriteMoviesActivity::class.java)
            startActivity(intent)
        }

        binding.btnTema.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "opções de tema",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnSobre.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "oiii",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
