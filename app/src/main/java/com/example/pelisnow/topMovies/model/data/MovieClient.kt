package com.example.pelisnow.topMovies.model.data

import com.example.pelisnow.topMovies.model.MovieApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(MovieApiInterface::class.java)
}