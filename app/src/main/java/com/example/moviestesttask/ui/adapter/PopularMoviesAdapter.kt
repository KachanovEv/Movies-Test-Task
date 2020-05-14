package com.example.moviestesttask.ui.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.moviestesttask.R
import com.example.moviestesttask.data.model.PopularMoviesUiModel
import com.example.moviestesttask.ui.fragment.movies.PopularMoviesFragmentDirections
import kotlinx.android.synthetic.main.item_popular_movies.view.*

class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>() {

    private val popularMoviesList = ArrayList<PopularMoviesUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movies, parent, false)
        return PopularMoviesViewHolder(root)
    }

    override fun getItemCount(): Int {
        return popularMoviesList.size
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(popularMoviesList[position])
    }

    inner class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(popularMoviesUiModel: PopularMoviesUiModel) {

            Glide.with(itemView.context)
                .asDrawable()
                .load(popularMoviesUiModel.posterImage)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        itemView.iconIv.background = resource
                    }
                })

            itemView.titleTv.text = popularMoviesUiModel.title
            itemView.releaseDateTv.text = popularMoviesUiModel.releaseDate

            itemView.setOnClickListener {

                val action =
                    PopularMoviesFragmentDirections.actionNavigationArticlesToNavigationDetailsMovies()
                        .setContent(popularMoviesUiModel.description)
                        .setTitle(popularMoviesUiModel.title)

                itemView.findNavController().navigate(action)
            }

        }
    }

    fun setPopularMoviesData(moviesList: List<PopularMoviesUiModel>) {
        popularMoviesList.clear()
        popularMoviesList.addAll(moviesList)
        Log.d("adapter", popularMoviesList.size.toString())
        notifyDataSetChanged()
    }
}