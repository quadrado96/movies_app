package com.quadrado.movies_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.quadrado.movies_app.database.Database
import com.quadrado.movies_app.databinding.ActivityMainBinding
import com.quadrado.movies_app.util.ThemeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeUtils.applyTheme(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        CoroutineScope(Dispatchers.IO).launch {
            checkUser(this@MainActivity)
        }
    }

    override fun onResume() {
        CoroutineScope(Dispatchers.IO).launch {
            checkUser(this@MainActivity)
        }
        super.onResume()
    }

    suspend fun checkUser(context: Context) {
        val db = Database.getInstance(context)
        val dao = db.userDAO()
        val listaUsers = dao.getAllUsers()

        if (listaUsers.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                AlertDialog.Builder(context).apply {
                    setTitle("Conta necessária")
                    setMessage("Você ainda não criou uma conta.")
                    setCancelable(false)
                    setPositiveButton("Criar conta") { _, _ ->
                        val intent = Intent(context, CreateAccountActivity::class.java)
                        context.startActivity(intent)
                    }
                    setNegativeButton("Fechar") { _, _ ->
                        CoroutineScope(Dispatchers.IO).launch {
                            checkUser(context)
                        }
                    }
                    show()
                }
            }
        }
    }
}
