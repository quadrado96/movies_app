package com.quadrado.movies_app.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    var favorited: Boolean? = false
) : Parcelable
