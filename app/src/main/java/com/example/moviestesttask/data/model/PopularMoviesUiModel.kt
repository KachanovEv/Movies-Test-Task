package com.example.moviestesttask.data.model

data class PopularMoviesUiModel(
    var id: Int,
    var posterImage: String,
    var title: String,
    var description: String,
    var releaseDate: String,
    var pageNumber: Int,
    var pageCount: Int
)