package com.example.moviestesttask.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies_table")
data class PopularMoviesEntity(
    @PrimaryKey
    var id: Int?,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "image_path")
    var imagePath: String?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "release_date")
    var releaseDate: String?,
    @ColumnInfo(name = "page_number")
    var pageNumber: Int?,
    @ColumnInfo(name = "page_count")
    var pageCount: Int?

)