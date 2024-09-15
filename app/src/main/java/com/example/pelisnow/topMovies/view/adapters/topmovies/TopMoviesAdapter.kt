package com.example.pelisnow.topMovies.view.adapters.topmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pelisnow.R
import com.example.pelisnow.topMovies.model.data.topratedmovies.Result

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
    class TopMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title_movie)
        private val tvRating: TextView = itemView.findViewById(R.id.tv_rating)
        private val posterImageView: ImageView = itemView.findViewById(R.id.iv_poster_path)

        fun bind(movie: Result) {
            tvTitle.text = movie.title
            tvRating.text = movie.vote_average.toString()
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(posterImageView)
        }
    }
}