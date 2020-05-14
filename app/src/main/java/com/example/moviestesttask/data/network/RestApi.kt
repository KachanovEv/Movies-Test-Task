package com.example.moviestesttask.data.network


import com.example.moviestesttask.data.network.model.PopularMoviesModel
import com.example.moviestesttask.data.network.model.RequestUserTokenModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RestApi {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Response<PopularMoviesModel>


    @GET("/3/authentication/token/new")
    suspend fun createRequestToken(
        @Query("api_key") api_key: String
    ): Response<RequestUserTokenModel>

    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String?): Response<ResponseBody>

}