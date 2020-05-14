package com.example.moviestesttask.data.mapper

import com.example.moviestesttask.BuildConfig
import com.example.moviestesttask.data.db.model.PopularMoviesEntity
import com.example.moviestesttask.data.model.PopularMoviesUiModel
import com.example.moviestesttask.data.network.model.PopularMoviesModel
import com.example.moviestesttask.data.network.model.Result

object MoviesContentMapper {

    private val popularMoviesUiList = ArrayList<PopularMoviesUiModel>()

    fun setDataFromNetwork(popularMoviesModel: PopularMoviesModel): ArrayList<PopularMoviesUiModel> {

        for (item in popularMoviesModel.results!!) {

            popularMoviesUiList.add(
                PopularMoviesUiModel(
                    item.id!!,
                    BuildConfig.BASE_IMAGE_URL + item.posterPath!!,
                    item.title!!,
                    item.overview!!,
                    item.releaseDate!!,
                    popularMoviesModel.page!!,
                    popularMoviesModel.totalPages!!
                )
            )
        }
        return popularMoviesUiList
    }

    fun setDataFromDB(popularMoviesList: List<PopularMoviesEntity>): ArrayList<PopularMoviesUiModel> {
        for (item in popularMoviesList) {
            popularMoviesUiList.add(
                PopularMoviesUiModel(
                    item.id!!,
                    item.imagePath!!,
                    item.title!!,
                    item.description!!,
                    item.releaseDate!!,
                    item.pageNumber!!,
                    item.pageCount!!
                )
            )
        }
        return popularMoviesUiList
    }

    fun setDataToDBFromNetwork(
        popularMoviesModel: Result,
        pathFile: String,
        page: Int?,
        totalPages: Int?
    ): PopularMoviesEntity {

        return PopularMoviesEntity(
            popularMoviesModel.id!!,
            popularMoviesModel.title!!,
            pathFile,
            popularMoviesModel.overview!!,
            popularMoviesModel.releaseDate!!,
            page,
            totalPages
        )
    }
}