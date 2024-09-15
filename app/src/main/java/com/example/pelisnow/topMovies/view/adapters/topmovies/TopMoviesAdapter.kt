package com.example.pelisnow.topMovies.view.adapters.topmovies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pelisnow.R
import com.example.pelisnow.topMovies.model.data.topratedmovies.Result
import com.example.pelisnow.topMovies.view.MovieDetailFragment

class TopMoviesAdapter(var topMovies: List<Result>) : RecyclerView.Adapter<TopMoviesAdapter.TopMoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMoviesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movies_cards, parent, false)
        return TopMoviesViewHolder(itemView)
    }

    override fun getItemCount(): Int = topMovies.size

    override fun onBindViewHolder(holder: TopMoviesViewHolder, position: Int) {
        val movie = topMovies[position]
        holder.bind(movie)
    }

    //View Holder
    inner class TopMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_movie)
        private val tvRating: TextView = itemView.findViewById(R.id.tv_rating)
        private val posterImageView: ImageView = itemView.findViewById(R.id.iv_poster_path)

        @SuppressLint("DefaultLocale")
        fun bind(movie: Result) {
            tvTitle.text = movie.title
            tvRating.text = String.format("%.1f", movie.vote_average)
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(posterImageView)

            itemView.setOnClickListener {
                val movieDetailFragment = MovieDetailFragment.newInstance(movie.title, movie.vote_average, movie.poster_path, movie.overview)
                val fragmentManager = (itemView.context as? AppCompatActivity)?.supportFragmentManager

                fragmentManager?.beginTransaction()?.replace(R.id.fragment_container_view, movieDetailFragment)?.addToBackStack(null)?.commit()
            }
        }
    }
}