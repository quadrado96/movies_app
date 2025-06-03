package com.quadrado.movies_app.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quadrado.movies_app.MovieDetailsActivity
import com.quadrado.movies_app.R
import com.quadrado.movies_app.database.entities.FavoriteMovie

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<FavoriteMovie>()

    fun setMovies(newMovies: List<FavoriteMovie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePoster: ImageView = itemView.findViewById(R.id.img_poster__)
        val textTitle: TextView = itemView.findViewById(R.id.tv_title__)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.textTitle.text = movie.title

        val imageUrl = "https://image.tmdb.org/t/p/w185${movie.posterPath}"
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.landscape_placeholder_svgrepo_com)
            .error(R.drawable.ic_error_warning_fill)
            .into(holder.imagePoster)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("movie_id", movie.movieId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = movies.size
}
