package com.example.moviestesttask.di

import androidx.room.Room
import com.example.moviestesttask.data.db.MoviesAppDataBase
import com.example.moviestesttask.data.network.ApiManager
import com.example.moviestesttask.data.shared_pref.SharedManager
import com.example.moviestesttask.ui.fragment.movies.PopularMoviesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Instance  DB Room
    single {
        Room.databaseBuilder(get(), MoviesAppDataBase::class.java, "movies-database")
            .build()
    }

    single { get<MoviesAppDataBase>().popularMoviesDao() }

    // Retrofit
    single { ApiManager(androidContext()) }

    // SharedManager
    single { SharedManager(androidContext()) }

    // viewModel
    viewModel { PopularMoviesViewModel(get()) }
}