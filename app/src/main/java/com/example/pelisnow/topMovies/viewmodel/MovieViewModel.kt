package com.example.pelisnow.topMovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pelisnow.topMovies.model.data.MovieClient
import com.example.pelisnow.topMovies.model.data.moviesintheaters.MovieTheater
import com.example.pelisnow.topMovies.model.data.topratedmovies.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val _topMovies = MutableLiveData<List<Result>>()
    val topMovies: LiveData<List<Result>> = _topMovies

    private val _moviesInTheater = MutableLiveData<List<MovieTheater>>()
    val moviesInTheater: LiveData<List<MovieTheater>> = _moviesInTheater

    private var originalTopMovies: List<Result> = emptyList()
    private var originalTheaterMovies: List<MovieTheater> = emptyList()

    fun fetchMovies(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val topMoviesResponse = MovieClient.service.listTopMovies(apiKey)
            val bodyTop = topMoviesResponse.execute().body()
            val moviesInTheaterResponse = MovieClient.service.listMoviesInTheaters(apiKey)
            val bodyTheater = moviesInTheaterResponse.execute().body()

            originalTopMovies = bodyTop?.results ?: emptyList()
            _topMovies.postValue(bodyTop?.results ?: emptyList())

            originalTheaterMovies = bodyTheater?.moviesinTheater ?: emptyList()
            _moviesInTheater.postValue(bodyTheater?.moviesinTheater ?: emptyList())
        }
    }

    fun filterMovies(query: String?) {
        val filteredTopMovies =
            if (!query.isNullOrEmpty()) {
                originalTopMovies.filter { movieTop ->
                    movieTop.title.contains(query, ignoreCase = true)
                }
            } else {
             originalTopMovies
            }

        val filteredTheaterMovies =
            if (!query.isNullOrEmpty()) {
                originalTheaterMovies.filter { movieTheater ->
                    movieTheater.title.contains(query, ignoreCase = true)
                }
            } else {
                originalTheaterMovies
            }

        _topMovies.postValue(filteredTopMovies)
        _moviesInTheater.postValue(filteredTheaterMovies)
    }
}