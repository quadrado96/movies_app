package com.quadrado.movies_app.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.quadrado.movies_app.R
import com.quadrado.movies_app.models.Movie

class MovieAdapter(
    private val movies: List<Movie>
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
        
        if ((movie.voteAverage ?: 0.0) <= 5) {
            holder.imgStar.setImageResource(R.drawable.ic_star_half_line)
        } else {
            holder.imgStar.setImageResource(R.drawable.ic_star_fill)
        }

        Log.d("MovieAdapter", "Movie: ${movie.title} - Vote: ${movie.voteAverage}")


        holder.btnFavorite.setOnClickListener {
            if(movie.favorited == false) {
                movie.favorited = true
                holder.btnFavorite.setImageResource(R.drawable.ic_heart_fill)
                Toast.makeText(
                    holder.itemView.context,
                    "Salvo nos favoritos!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                movie.favorited = false
                holder.btnFavorite.setImageResource(R.drawable.ic_heart_add_line)
                Toast.makeText(
                    holder.itemView.context,
                    "Removido dos favoritos!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int = movies.size
}
