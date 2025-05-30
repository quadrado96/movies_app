package com.quadrado.movies_app.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val username: String,
    val email: String,
    val password: String
)
