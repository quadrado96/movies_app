package com.quadrado.movies_app.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpokenLanguage(
    @Json(name = "english_name") val englishName: String,
    @Json(name = "iso_639_1") val iso: String,
    val name: String
): Parcelable