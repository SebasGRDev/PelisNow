package com.example.pelisnow.topMovies.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.pelisnow.R

private var ARG_TITLE = "title"
private var ARG_VOTE_AVERAGE = "vote_average"
private var ARG_POSTER_PATH = "poster_path"
private var ARG_OVERVIEW = "overview"

class MovieDetailFragment : Fragment() {

    private var title: String? = null
    private var voteAverage: String? = null
    private var posterPath: String? = null
    private var overview: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            voteAverage = it.getString(ARG_VOTE_AVERAGE)
            posterPath = it.getString(ARG_POSTER_PATH)
            overview = it.getString(ARG_OVERVIEW)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDetailScreen(view)
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun setDetailScreen(view: View) {
        val title = arguments?.getString(ARG_TITLE)
        val voteAverage = arguments?.getDouble(ARG_VOTE_AVERAGE)
        val posterPath = arguments?.getString(ARG_POSTER_PATH)
        val overview = arguments?.getString(ARG_OVERVIEW)

        val tvTitle = view.findViewById<TextView>(R.id.txt_detail_title_movie)
        val tvVoteAverage = view.findViewById<TextView>(R.id.txt_detail_rating)
        val ivPosterPath = view.findViewById<ImageView>(R.id.iv_detail_poster)
        val tvOverview = view.findViewById<TextView>(R.id.txt_detail_content)

        tvTitle.text = title
        tvVoteAverage.text = "${ String.format("%.1f", voteAverage) }/ 10"
        tvOverview.text = overview
        Glide.with(view.context)
            .load("https://image.tmdb.org/t/p/w500${posterPath}")
            .into(ivPosterPath)

        val ratingStar1 = view.findViewById<ImageView>(R.id.txt_detail_rating_star_1)
        val ratingStar2 = view.findViewById<ImageView>(R.id.txt_detail_rating_star_2)
        val ratingStar3 = view.findViewById<ImageView>(R.id.txt_detail_rating_star_3)
        val ratingStar4 = view.findViewById<ImageView>(R.id.txt_detail_rating_star_4)
        val ratingStar5 = view.findViewById<ImageView>(R.id.txt_detail_rating_star_5)

        setStars(voteAverage?.div(2), ratingStar1, ratingStar2, ratingStar3, ratingStar4, ratingStar5)
    }

    private fun setStars(rating: Double?, vararg stars: ImageView) {
        stars.forEachIndexed { index, imageView ->
            when {
                rating == null -> imageView.setImageResource(R.drawable.ic_star_empty)
                index + 1 <= rating -> imageView.setImageResource(R.drawable.ic_star)
                index + 0.5 <= rating -> imageView.setImageResource(R.drawable.ic_star_half)
                else -> imageView.setImageResource(R.drawable.ic_star_empty)
            }
        }
    }

    companion object {
        fun newInstance(title: String, voteAverage: Double, posterPath: String, overview: String) : MovieDetailFragment {
            val fragment = MovieDetailFragment()
            fragment.apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putDouble(ARG_VOTE_AVERAGE, voteAverage)
                    putString(ARG_POSTER_PATH, posterPath)
                    putString(ARG_OVERVIEW, overview)
                }
            }
            return fragment
        }
    }
}