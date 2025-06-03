package com.quadrado.movies_app

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quadrado.movies_app.adapters.FavoriteMovieAdapter
import com.quadrado.movies_app.database.Database
import kotlinx.coroutines.launch

class FavoriteMoviesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite_movies)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val botaoSair = findViewById<ImageButton>(R.id.btn_sair)
        botaoSair.setOnClickListener {
            finish()
        }

        val recyclerView: RecyclerView = findViewById(R.id.rv_favorite_movies)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val favoriteMoviesList = Database.getInstance(this@FavoriteMoviesActivity)
                .favoriteMovieDAO()
                .getAllFavorites()

            val adapter = FavoriteMovieAdapter()
            adapter.setMovies(favoriteMoviesList)

            recyclerView.adapter = adapter
        }
    }
}