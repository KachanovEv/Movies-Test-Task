package com.example.moviestesttask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviestesttask.data.db.dao.MoviesDao
import com.example.moviestesttask.data.db.model.PopularMoviesEntity

@Database(
    entities = [PopularMoviesEntity::class], version = 3
)

abstract class MoviesAppDataBase : RoomDatabase() {
    abstract fun popularMoviesDao(): MoviesDao
}