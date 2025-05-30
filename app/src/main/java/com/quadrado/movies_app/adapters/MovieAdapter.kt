package com.quadrado.movies_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quadrado.movies_app.R
import com.quadrado.movies_app.models.Movie

class MovieAdapter(
    private val movies: MutableList<Movie>,
    private val onFavoriteClick: (Movie, Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPoster: ImageView = view.findViewById(R.id.img_poster)
        val tvTitle: TextView = view.findViewById(R.id.tv_titulo)
        val tvRate: TextView = view.findViewById(R.id.tv_nota)
        val btnFavorite: ImageButton = view.findViewById(R.id.btn_favorito)
        val imgStar: ImageView = view.findViewById(R.id.img_estrela)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.tvTitle.text = movie.title
        holder.tvRate.text = String.format("%.1f", movie.voteAverage ?: 0.0)

        val imageUrl = "https://image.tmdb.org/t/p/w185${movie.posterPath}"
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.landscape_placeholder_svgrepo_com)
            .error(R.drawable.ic_error_warning_fill)
            .into(holder.ivPoster)

        if ((movie.voteAverage ?: 0.0) <= 5) {
            holder.imgStar.setImageResource(R.drawable.ic_star_half_line)
        } else {
            holder.imgStar.setImageResource(R.drawable.ic_star_fill)
        }

        holder.btnFavorite.setImageResource(
            if (movie.favorited == true)
                R.drawable.ic_heart_fill
            else
                R.drawable.ic_heart_add_line
        )

        holder.btnFavorite.setOnClickListener {
            onFavoriteClick(movie, position)
        }

    }

    fun updateMovieAt(position: Int, movie: Movie) {
        movies[position] = movie
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int = movies.size
}
