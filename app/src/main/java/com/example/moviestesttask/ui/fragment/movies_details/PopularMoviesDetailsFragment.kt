package com.example.moviestesttask.ui.fragment.movies_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviestesttask.R
import kotlinx.android.synthetic.main.fragment_movies_details.*

class PopularMoviesDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataFromArguments()
    }

    private fun setDataFromArguments() {
        if (arguments != null) {
            val content = PopularMoviesDetailsFragmentArgs.fromBundle(requireArguments()).content
            val title = PopularMoviesDetailsFragmentArgs.fromBundle(requireArguments()).title
            titleTv.text = title
            descriptionTv.text = content
        }
    }
}