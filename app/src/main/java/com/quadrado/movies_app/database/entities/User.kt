package com.quadrado.movies_app.database.entities

import androidx.room.Entity

@Entity(tableName = "user")
data class User(
    val username: String,
    val email: String,
    val password: String
)
