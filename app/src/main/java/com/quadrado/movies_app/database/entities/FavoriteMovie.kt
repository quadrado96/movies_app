package com.quadrado.movies_app.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String,
    val posterPath: String
)