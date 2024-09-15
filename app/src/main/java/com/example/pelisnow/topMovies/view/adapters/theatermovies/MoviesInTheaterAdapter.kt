package com.example.pelisnow.topMovies.view.adapters.theatermovies

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pelisnow.R
import com.example.pelisnow.topMovies.model.MoviesResponse
import com.example.pelisnow.topMovies.model.data.moviesintheaters.MovieTheater
import com.example.pelisnow.topMovies.model.data.moviesintheaters.MoviesTheatersResult
import com.example.pelisnow.topMovies.model.data.topratedmovies.Result
import com.example.pelisnow.topMovies.view.adapters.topmovies.TopMoviesAdapter.TopMoviesViewHolder

class MoviesInTheaterAdapter(var theaterMovies: List<MovieTheater>) : RecyclerView.Adapter<MoviesInTheaterAdapter.TheaterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movies_cards, parent, false)
        return TheaterViewHolder(itemView)
    }

    override fun getItemCount(): Int = theaterMovies.size

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        val movie = theaterMovies[position]
        holder.bind(movie)
    }

    class TheaterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_movie)
        private val tvRating: TextView = itemView.findViewById(R.id.tv_rating)
        private val posterImageView: ImageView = itemView.findViewById(R.id.iv_poster_path)

        fun bind(movie: MovieTheater) {
            tvTitle.text = movie.title
            tvRating.text = movie.vote_average.toString()
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(posterImageView)
        }
    }
}