package com.quadrado.movies_app.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCountry(
    @Json(name = "iso_3166_1") val iso: String,
    val name: String
): Parcelable
