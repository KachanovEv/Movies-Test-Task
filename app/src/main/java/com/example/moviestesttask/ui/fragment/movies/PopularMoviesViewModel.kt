package com.example.moviestesttask.ui.fragment.movies

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviestesttask.BuildConfig
import com.example.moviestesttask.data.db.MoviesAppDataBase
import com.example.moviestesttask.data.mapper.MoviesContentMapper
import com.example.moviestesttask.data.model.PopularMoviesUiModel
import com.example.moviestesttask.data.network.ApiManager
import com.example.moviestesttask.data.network.model.Result
import com.example.moviestesttask.data.shared_pref.SharedManager
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.koin.android.ext.android.inject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList


class PopularMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesDataBase: MoviesAppDataBase by application.inject()
    private val context = application.applicationContext
    val popularMoviesLiveData = MutableLiveData<ArrayList<PopularMoviesUiModel>>()

    fun getToken() {
        viewModelScope.launch(IO) {
            val response =
                ApiManager(context).restApiNoHeader().createRequestToken(BuildConfig.API_KEY)

            withContext(Main) {
                SharedManager(context).token = response.body()!!.requestToken!!
            }
        }
    }

    fun getPopularMovies(page: Int) {
        viewModelScope.launch(IO) {
            val language = Locale.getDefault().toLanguageTag()
            val region = ""
            val response =
                ApiManager(context).restApiNoHeader().getPopularMovies(
                    BuildConfig.API_KEY,
                    language,
                    page,
                    region
                )

            withContext(Main) {
                popularMoviesLiveData.value =
                    MoviesContentMapper.setDataFromNetwork(response.body()!!)

                    for (item in response.body()!!.results!!) {
                        downloadFileFromRetrofit(
                            item,
                            BuildConfig.BASE_IMAGE_URL + item.posterPath!!,
                             page,
                            response.body()!!.totalPages
                        )
                    }

            }
        }
    }

    private fun downloadFileFromRetrofit(
        popularMoviesData: Result,
        picture: String,
        page: Int?,
        totalPages: Int?
    ) {
        viewModelScope.launch(IO) {
            val result = ApiManager(context).restApiNoHeader().downloadFile(picture)

            val fileName = picture.substring(
                picture.lastIndexOf("/") + 1
            )

            val directory = File(context?.filesDir!!.absolutePath)
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val pathFile =
                directory.absolutePath + "/" + fileName

            saveFile(result.body(), pathFile)
            insertPopularMoviesDataToDB(popularMoviesData, pathFile, page, totalPages)
        }
    }

    private fun saveFile(body: ResponseBody?, pathWhereYouWantToSaveFile: String): String {
        if (body == null)
            return ""
        var inputStream: InputStream? = null
        try {
            inputStream = body.byteStream()

            val fileOutputStream = FileOutputStream(pathWhereYouWantToSaveFile)
            fileOutputStream.use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return pathWhereYouWantToSaveFile
        } catch (e: Exception) {
            Log.e("saveFile", e.toString())
        } finally {
            inputStream?.close()
        }
        return ""
    }

    private fun insertPopularMoviesDataToDB(
        popularMoviesData: Result,
        pathFile: String,
        page: Int?,
        totalPages: Int?
    ) {
        moviesDataBase.popularMoviesDao()
            .insertPopularMoviesData(
                MoviesContentMapper.setDataToDBFromNetwork(
                    popularMoviesData,
                    pathFile,
                    page,
                    totalPages
                )
            )

    }

    fun getPopularMoviesDataFromDB(pageNumber:Int) {
        viewModelScope.launch(IO) {
            val result = moviesDataBase.popularMoviesDao().getAllPopularMoviesData(pageNumber)
            withContext(Main) {
                popularMoviesLiveData.value = MoviesContentMapper.setDataFromDB(result)
            }
        }
    }

}