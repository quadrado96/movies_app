package com.quadrado.movies_app.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.quadrado.movies_app.AccountActivity
import com.quadrado.movies_app.FavoriteMoviesActivity
import com.quadrado.movies_app.InfoActivity
import com.quadrado.movies_app.R
import com.quadrado.movies_app.databinding.FragmentSettingsBinding
import com.quadrado.movies_app.database.Database
import com.quadrado.movies_app.util.ThemeUtils
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
            showThemeSelector()
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

    private fun showThemeSelector() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_theme, null)
        bottomSheet.setContentView(view)

        view.findViewById<View>(R.id.btnTemaClaro).setOnClickListener {
            ThemeUtils.setTheme(requireContext(), ThemeUtils.THEME_LIGHT)
            bottomSheet.dismiss()
            restartApp()
        }

        view.findViewById<View>(R.id.btnTemaEscuro).setOnClickListener {
            ThemeUtils.setTheme(requireContext(), ThemeUtils.THEME_DARK)
            bottomSheet.dismiss()
            restartApp()
        }

        view.findViewById<View>(R.id.btnTemaSistema).setOnClickListener {
            ThemeUtils.setTheme(requireContext(), ThemeUtils.THEME_SYSTEM)
            bottomSheet.dismiss()
            restartApp()
        }

        bottomSheet.show()
    }

    private fun restartApp() {
        val intent = requireActivity().intent
        requireActivity().finish()
        startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
