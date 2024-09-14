package com.example.pelisnow.topMovies.data

import com.example.pelisnow.topMovies.model.RemoteResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun listTopMovies(
        @Query("api_key") api_key: String
    ) : RemoteResult

    object RetrofitServiceFactory {
        fun makeRetrofitService() : MovieService {
            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MovieService::class.java)
        }
    }
}