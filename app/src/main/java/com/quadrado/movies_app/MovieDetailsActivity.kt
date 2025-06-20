package com.quadrado.movies_app

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.quadrado.movies_app.database.Database
import com.quadrado.movies_app.database.entities.FavoriteMovie
import com.quadrado.movies_app.models.MovieDetails
import com.quadrado.movies_app.repository.MovieRepository
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var ivBackdrop: ImageView
    private lateinit var ivPoster: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvTagline: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvOverview: TextView
    private lateinit var tvInfo: TextView
    private lateinit var tvGenres: TextView
    private lateinit var tvCompanies: TextView
    private lateinit var tvCountries: TextView
    private lateinit var tvRevenue: TextView
    private lateinit var tvBudget: TextView
    private lateinit var btnSalvar: Button

    private lateinit var dao: com.quadrado.movies_app.database.dao.FavoriteMovieDAO
    private var isFavorite = false
    private var currentMovie: MovieDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scrollView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val db = Database.getInstance(this)
        dao = db.favoriteMovieDAO()

        bindViews()

        val movieId = intent.getIntExtra("movie_id", -1)
        if (movieId == -1) {
            Toast.makeText(this, "Filme inválido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        fetchMovieDetails(movieId)
    }

    private fun bindViews() {
        ivBackdrop = findViewById(R.id.img_backdrop)
        ivPoster = findViewById(R.id.img_poster_details)
        tvTitle = findViewById(R.id.tv_title)
        tvTagline = findViewById(R.id.tv_tagline)
        tvRating = findViewById(R.id.tv_rating)
        tvOverview = findViewById(R.id.tv_overview)
        tvInfo = findViewById(R.id.tv_info)
        tvGenres = findViewById(R.id.tv_genres)
        tvCompanies = findViewById(R.id.tv_companies)
        tvCountries = findViewById(R.id.tv_countries)
        tvRevenue = findViewById(R.id.tv_revenue)
        tvBudget = findViewById(R.id.tv_budget)
        btnSalvar = findViewById(R.id.btn_salvar_favorito)
    }

    private fun fetchMovieDetails(movieId: Int) {
        lifecycleScope.launch {
            try {
                val movie = MovieRepository().getMovieDetails(movieId)
                currentMovie = movie
                checkIfFavorite(movie.id)
                populateUI(movie)
                setupFavoriteButton()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MovieDetailsActivity, "Erro ao carregar detalhes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun checkIfFavorite(movieId: Int) {
        val favoritos = dao.getAllFavorites()
        isFavorite = favoritos.any { it.movieId == movieId }
    }

    private fun setupFavoriteButton() {
        updateFavoriteButtonText()
        btnSalvar.setOnClickListener {
            currentMovie?.let { movie ->
                lifecycleScope.launch {
                    val favoriteMovie = FavoriteMovie(
                        movieId = movie.id,
                        title = movie.title,
                        posterPath = movie.posterPath ?: ""
                    )
                    if (isFavorite) {
                        dao.removeFavorite(favoriteMovie)
                        Toast.makeText(this@MovieDetailsActivity, "Removido dos favoritos", Toast.LENGTH_SHORT).show()
                    } else {
                        dao.addFavorite(favoriteMovie)
                        Toast.makeText(this@MovieDetailsActivity, "Adicionado aos favoritos", Toast.LENGTH_SHORT).show()
                    }
                    isFavorite = !isFavorite
                    updateFavoriteButtonText()
                }
            }
        }
    }

    private fun updateFavoriteButtonText() {
        val textRes = if (isFavorite) R.string.remover_favorito else R.string.salvar_favorito
        btnSalvar.setText(textRes)
    }

    private fun populateUI(movie: MovieDetails) {
        tvTitle.text = movie.title
        tvTagline.text = movie.tagline ?: ""
        tvRating.text = "Nota: %.3f (%s votos)".format(
            movie.voteAverage,
            formatNumber(movie.voteCount)
        )

        tvOverview.text = movie.overview ?: "Sem sinopse disponível."
        tvGenres.text = "Gêneros: ${movie.genres.joinToString { it.name }}"
        tvCompanies.text = "Produtoras: ${movie.productionCompanies.joinToString { it.name }}"
        tvCountries.text = "Países: ${movie.productionCountries.joinToString { it.name }}"
        tvRevenue.text = "Receita: ${formatCurrency(movie.revenue)}"
        tvBudget.text = "Orçamento: ${formatCurrency(movie.budget)}"

        val runtimeText = movie.runtime?.let { "$it min" } ?: "Duração desconhecida"
        val releaseDateText = formatDate(movie.releaseDate)
        tvInfo.text = "Lançado em $releaseDateText, $runtimeText"

        val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
        Glide.with(this)
            .load(imageBaseUrl + movie.backdropPath)
            .placeholder(R.drawable.landscape_placeholder_svgrepo_com)
            .into(ivBackdrop)

        Glide.with(this)
            .load(imageBaseUrl + movie.posterPath)
            .placeholder(R.drawable.landscape_placeholder_svgrepo_com)
            .into(ivPoster)
    }

    private fun formatDate(input: String?): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            input?.let { formatter.format(parser.parse(it)!!) } ?: "Data desconhecida"
        } catch (e: Exception) {
            "Data inválida"
        }
    }

    private fun formatCurrency(value: Long?): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formatter.format(value ?: 0)
    }

    private fun formatNumber(value: Int?): String {
        val formatter = NumberFormat.getIntegerInstance(Locale("pt", "BR"))
        return formatter.format(value ?: 0)
    }
}
