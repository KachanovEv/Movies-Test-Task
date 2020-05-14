package com.example.moviestesttask.data.shared_pref

import android.content.Context
import android.content.SharedPreferences

class SharedManager(private val context: Context) {

    private val PREFERENCES_NAME = "movies"
    private val USER_TOKEN = "USER_TOKEN"
    private val DEFAULT = "DEFAULT"


    private fun get(): SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var token: String
        get() = get().getString(USER_TOKEN, DEFAULT)!!
        set(value) = get().edit()
            .putString(USER_TOKEN, value)
            .apply()

}