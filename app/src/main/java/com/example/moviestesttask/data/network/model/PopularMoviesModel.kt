package com.example.moviestesttask.data.network.model

import com.google.gson.annotations.SerializedName

data class PopularMoviesModel(
    @field:SerializedName("page")
    var page: Int? = null,

    @field:SerializedName("total_results")
    var totalResults: Int? = null,

    @field:SerializedName("total_pages")
    var totalPages: Int? = null,

    @field:SerializedName("results")
    var results: List<Result>? = null

)

data class Result(
    @field:SerializedName("popularity")
    var popularity: Double? = null,

    @field:SerializedName("vote_count")
    var voteCount: Int? = null,

    @field:SerializedName("video")
    var video: Boolean? = null,

    @field:SerializedName("poster_path")
    var posterPath: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("adult")
    var adult: Boolean? = null,

    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @field:SerializedName("original_language")
    var originalLanguage: String? = null,

    @field:SerializedName("original_title")
    var originalTitle: String? = null,

    @field:SerializedName("genre_ids")
    var genreIds: List<Int>? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("vote_average")
    var voteAverage: Double? = null,

    @field:SerializedName("overview")
    var overview: String? = null,

    @field:SerializedName("release_date")
    var releaseDate: String? = null
)