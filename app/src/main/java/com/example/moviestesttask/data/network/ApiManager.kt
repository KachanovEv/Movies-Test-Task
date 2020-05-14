package com.example.moviestesttask.data.network


import android.content.Context
import com.example.moviestesttask.BuildConfig
import com.example.moviestesttask.data.shared_pref.SharedManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class ApiManager(context: Context) {

    private var restApiHeader: RestApi
    private var restApi: RestApi
    private var gson: Gson
    private var readTimeout = 1L
    private var writeimeout = 2L

    init {
        gson = createGson()
        restApiHeader = Retrofit.Builder().apply {
            baseUrl(BuildConfig.SERVER_URL)
            client(initClient(true, context))
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build().create(RestApi::class.java)

        restApi = Retrofit.Builder().apply {
            baseUrl(BuildConfig.SERVER_URL)
            client(initClient(false, context))
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build().create(RestApi::class.java)
    }

    private fun initClient(needToken: Boolean, context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(interceptor)
            readTimeout(readTimeout, TimeUnit.MINUTES)
            connectTimeout(readTimeout, TimeUnit.MINUTES)
            writeTimeout(writeimeout, TimeUnit.MINUTES)
            addNetworkInterceptor { chain ->
                val request: Request
                val original = chain.request()
                val originalBuilder = original.newBuilder()
                originalBuilder.addHeader("Content-PresentationsType", "application/json")
                if (SharedManager(context).token.isNotEmpty() && needToken) {
                    originalBuilder.addHeader(
                        "Authorization", "Bearer ${SharedManager(
                            context
                        ).token}"
                    )
                }
                request = originalBuilder.build()
                chain.proceed(request)
            }
        }.build()
    }

    private fun createGson(): Gson {
        return GsonBuilder().apply {
            setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            setLenient()
        }.create()
    }

    fun restApiNoHeader(): RestApi {
        return restApi
    }

    fun restApiHeader(): RestApi {
        return restApiHeader
    }

}