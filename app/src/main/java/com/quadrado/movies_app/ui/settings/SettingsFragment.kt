package com.quadrado.movies_app.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.quadrado.movies_app.AccountActivity
import com.quadrado.movies_app.FavoriteMoviesActivity
import com.quadrado.movies_app.InfoActivity
import com.quadrado.movies_app.databinding.FragmentSettingsBinding
import com.quadrado.movies_app.database.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        loadUserData()

        return root
    }

    private fun setupListeners() {
        binding.frmUser.setOnClickListener {
            val intent = Intent(requireContext(), AccountActivity::class.java)
            startActivity(intent)
        }

        binding.btnFavoritos.setOnClickListener {
            val intent = Intent(requireContext(), FavoriteMoviesActivity::class.java)
            startActivity(intent)
        }

        binding.btnTema.setOnClickListener {
            Toast.makeText(requireContext(), "opções de tema", Toast.LENGTH_SHORT).show()
        }

        binding.btnSobre.setOnClickListener {
            val intent = Intent(requireContext(), InfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = Database.getInstance(requireContext()).userDAO()
            val users = dao.getAllUsers()

            if (users.isNotEmpty()) {
                val user = users.first()
                withContext(Dispatchers.Main) {
                    binding.tvUser.text = user.username
                    binding.tvEmail.text = user.email
                }
            } else {
                withContext(Dispatchers.Main) {
                    binding.tvUser.text = "Usuário não encontrado"
                    binding.tvEmail.text = ""
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
