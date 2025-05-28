package com.quadrado.movies_app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.quadrado.movies_app.database.entities.User

@Dao
interface UserDAO {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT username FROM user")
    suspend fun getUsername(): String

    @Query("SELECT email FROM user")
    suspend fun getEmail(): String

}