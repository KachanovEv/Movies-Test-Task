package com.example.moviestesttask.ui.fragment.movies

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviestesttask.R
import com.example.moviestesttask.ui.adapter.PopularMoviesAdapter
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularMoviesFragment : Fragment() {

    private val moviesAdapter = PopularMoviesAdapter()
    private val moviesViewModel: PopularMoviesViewModel by viewModel()
    private var pageNumber = 2
    private var pagesCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        checkInternetConnection()
        setDataToAdapter()
        setRecyclerViewScrollListener()
    }

    private fun checkInternetConnection() {
        if (isOnline(requireContext())) {
            // get token for working with API
            moviesViewModel.getToken()
            // get popular movies data
            // set page = 1 for first init
            moviesViewModel.getPopularMovies(1)
        } else {
            // get data from Local Storage
            // set page = 1 for first init
            moviesViewModel.getPopularMoviesDataFromDB(1)
        }
    }

    private fun initAdapter() {
        rvMovies.adapter = moviesAdapter
        rvMovies.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvMovies.layoutManager = layoutManager
    }

    private fun setDataToAdapter() {
        // observe data from network
        moviesViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer { result ->
            // set data to adapter
            moviesAdapter.setPopularMoviesData(result)
            for (item in result) {
                pagesCount = item.pageCount
            }
        })
    }

    private fun setRecyclerViewScrollListener() {

        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val ll = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPos = ll.findLastVisibleItemPosition()

                if (firstVisibleItemPos == (totalItemCount - 1)) {
                    if (isOnline(requireContext())) {
                        moviesViewModel.getPopularMovies(pageNumber)
                    } else {
                        moviesViewModel.getPopularMoviesDataFromDB(pageNumber)
                    }

                    // control count of page for net query
                    if (pageNumber <= (pagesCount - 1)) {
                        pageNumber += 1
                    }
                }
            }
        })
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}