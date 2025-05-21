package com.quadrado.movies_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quadrado.movies_app.R
import com.quadrado.movies_app.models.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList: List<Category> = emptyList()

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvCategoryTitle)
        val rvMovies: RecyclerView = view.findViewById(R.id.rvMovies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_section, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.tvTitle.text = category.title

        val movieAdapter = MovieAdapter(category.movies)
        holder.rvMovies.layoutManager = LinearLayoutManager(
            holder.itemView.context,
            RecyclerView.HORIZONTAL,
            false
        )
        holder.rvMovies.adapter = movieAdapter
    }

    override fun getItemCount(): Int = categoryList.size

    fun setData(categories: List<Category>) {
        categoryList = categories
        notifyDataSetChanged()
    }
}
