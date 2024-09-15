package com.example.pelisnow.topMovies.model.data.moviesintheaters

import com.google.gson.annotations.SerializedName

data class MoviesTheatersResult(
    val dates: Dates,
    val page: Int,
    @SerializedName("results")
    val moviesinTheater: List<MovieTheater>,
    val total_pages: Int,
    val total_results: Int
)