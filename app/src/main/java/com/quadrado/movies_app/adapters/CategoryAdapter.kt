package com.quadrado.movies_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quadrado.movies_app.R
import com.quadrado.movies_app.models.Category
import com.quadrado.movies_app.models.Movie

class CategoryAdapter(
    var onFavoriteClick: ((Movie, Int) -> Unit)? = null
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val categories = mutableListOf<Category>()

    fun setData(newCategories: List<Category>) {
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvCategoryTitle)
        val recycler: RecyclerView = view.findViewById(R.id.rvMovies)
        var movieAdapter: MovieAdapter? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_section, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.title.text = category.title
        holder.recycler.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)

        val movieAdapter = MovieAdapter(category.movies.toMutableList()) { movie, moviePosition ->
            onFavoriteClick?.invoke(movie, moviePosition)
            holder.movieAdapter?.updateMovieAt(moviePosition, movie)
        }

        holder.movieAdapter = movieAdapter
        holder.recycler.adapter = movieAdapter
    }

    fun setFavoriteClickListener(listener: (Movie, Int) -> Unit) {
        this.onFavoriteClick = listener
    }

    override fun getItemCount(): Int = categories.size
}

