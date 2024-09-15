package com.example.pelisnow.topMovies.model.database.topmovies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesTopDb(
    @PrimaryKey
    val title: String,
    val vote_average: Double,
    val poster_path: String,
    val overview: String
)
