package com.quadrado.movies_app.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCompany(
    val id: Int,
    val name: String,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "origin_country") val originCountry: String
): Parcelable
