package com.quadrado.movies_app.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quadrado.movies_app.database.entities.FavoriteMovie

@Dao
interface FavoriteMovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(movie: FavoriteMovie)

    @Delete
    suspend fun removeFavorite(movie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movie ORDER BY title")
    suspend fun getAllFavorites(): List<FavoriteMovie>

}