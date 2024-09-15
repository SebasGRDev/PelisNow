package com.example.pelisnow.topMovies.model.data

import com.example.pelisnow.topMovies.model.MovieApiInterface
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

object MovieClient {
    val okHttpClient = OkHttpClient.Builder()
        .dispatcher(Dispatcher(Executors.newFixedThreadPool(5))) // Utiliza un pool de hilos
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(MovieApiInterface::class.java)


}