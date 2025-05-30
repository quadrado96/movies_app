package com.quadrado.movies_app.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.quadrado.movies_app.database.dao.FavoriteMovieDAO
import com.quadrado.movies_app.database.dao.UserDAO
import com.quadrado.movies_app.database.entities.FavoriteMovie
import com.quadrado.movies_app.database.entities.User

@androidx.room.Database(
    entities = [User::class, FavoriteMovie::class],
    version = 2,
    exportSchema = false
)
abstract class Database: RoomDatabase() {

    abstract fun userDAO(): UserDAO
    abstract fun favoriteMovieDAO(): FavoriteMovieDAO

    companion object {

        private var database: Database? = null
        private val DATABASE = "movies_app_db"

        fun getInstance(context: Context): Database {
            if (database == null) {
                database = criaBanco(context)
            }
            return database!!
        }

        private fun criaBanco(context: Context): Database? {
            return Room.databaseBuilder(
                context, Database::class.java, DATABASE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration(false)
                .build()
        }
    }


}