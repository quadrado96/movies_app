package com.quadrado.movies_app.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(
    val id: Int,

    val title: String,

    @Json(name = "original_title")
    val originalTitle: String,

    val overview: String?,

    val tagline: String?,

    val status: String?,

    @Json(name = "release_date")
    val releaseDate: String?,

    val runtime: Int?,

    val budget: Long?,

    val revenue: Long?,

    val popularity: Float?,

    @Json(name = "vote_average")
    val voteAverage: Float,

    @Json(name = "vote_count")
    val voteCount: Int,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "original_language")
    val originalLanguage: String,

    @Json(name = "imdb_id")
    val imdbId: String?,

    @Json(name = "homepage")
    val homepage: String?,

    val adult: Boolean,

    val video: Boolean,

    @Json(name = "belongs_to_collection")
    val belongsToCollection: CollectionInfo?,

    val genres: List<Genre> = emptyList(),

    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),

    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),

    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = emptyList()
): Parcelable
