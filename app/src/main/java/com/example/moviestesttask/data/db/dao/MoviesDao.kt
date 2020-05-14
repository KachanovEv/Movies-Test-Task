package com.example.moviestesttask.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviestesttask.data.db.model.PopularMoviesEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularMoviesData(moviesEntityList: PopularMoviesEntity)

    @Query("SELECT * FROM popular_movies_table WHERE page_number=:pageNumber")
    fun getAllPopularMoviesData(pageNumber: Int): List<PopularMoviesEntity>
}