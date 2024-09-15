package com.example.pelisnow.topMovies.model.database.moviesintheater

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesTheaterDb(
    @PrimaryKey
    val title: String,
    val vote_average: Double,
    val poster_path: String,
    val overview: String
)
