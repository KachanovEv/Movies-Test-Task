package com.example.moviestesttask.core

import android.app.Application
import com.example.moviestesttask.di.appModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //  Koin
        startKoin {
            androidContext(this@MoviesApp)
            modules(appModule)
        }

        //  Stetho
        Stetho.initializeWithDefaults(this)

    }

}