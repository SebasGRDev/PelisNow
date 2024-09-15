package com.example.pelisnow.topMovies.model

import com.example.pelisnow.topMovies.model.data.topratedmovies.RemoteResult
import com.example.pelisnow.topMovies.model.data.moviesintheaters.MoviesTheatersResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {

    @GET("movie/top_rated")
    fun listTopMovies(@Query("api_key")apiKey: String): Call<RemoteResult>

    @GET("movie/now_playing")
    fun listMoviesInTheaters(@Query("api_key")apiKey: String): Call<MoviesTheatersResult>
}