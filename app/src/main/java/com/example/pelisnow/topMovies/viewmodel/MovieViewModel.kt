package com.example.pelisnow.topMovies.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pelisnow.topMovies.model.data.MovieClient
import com.example.pelisnow.topMovies.model.data.moviesintheaters.MovieTheater
import com.example.pelisnow.topMovies.model.data.topratedmovies.Result
import com.example.pelisnow.topMovies.model.database.topmovies.MoviesTopDb
import com.example.pelisnow.topMovies.model.database.MoviesDatabase
import com.example.pelisnow.topMovies.model.database.moviesintheater.MoviesTheaterDb
import com.example.pelisnow.topMovies.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val _topMovies = MutableLiveData<List<Result>>()
    val topMovies: LiveData<List<Result>> = _topMovies

    private val _moviesInTheater = MutableLiveData<List<MovieTheater>>()
    val moviesInTheater: LiveData<List<MovieTheater>> = _moviesInTheater

    private var originalTopMovies: List<Result> = emptyList()
    private var originalTheaterMovies: List<MovieTheater> = emptyList()

    //Databse
    private val movieDao = MoviesDatabase.getDatabase(application).movieDao()
    private val moviesTheaterDao = MoviesDatabase.getDatabase(application).moviesTheaterDao()

    fun fetchMovies(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val topMoviesResponse = MovieClient.service.listTopMovies(apiKey)
            val bodyTop = topMoviesResponse.execute().body()
            val moviesInTheaterResponse = MovieClient.service.listMoviesInTheaters(apiKey)
            val bodyTheater = moviesInTheaterResponse.execute().body()

            originalTopMovies = bodyTop?.results ?: emptyList()
            _topMovies.postValue(bodyTop?.results ?: emptyList())

            //Guarda los datos en la database
            originalTopMovies.forEach { topMovie ->
                movieDao.insertMovies(
                    MoviesTopDb(
                        title = topMovie.title,
                        vote_average = topMovie.vote_average,
                        poster_path = topMovie.poster_path,
                        overview = topMovie.overview
                    )
                )
            }

            originalTheaterMovies = bodyTheater?.moviesinTheater ?: emptyList()
            _moviesInTheater.postValue(bodyTheater?.moviesinTheater ?: emptyList())

            originalTheaterMovies.forEach { movieTheater ->
                moviesTheaterDao.insertMoviesTheater(
                    MoviesTheaterDb(
                        title = movieTheater.title,
                        vote_average = movieTheater.vote_average,
                        poster_path = movieTheater.poster_path,
                        overview = movieTheater.overview
                    )
                )
            }
        }
    }

    fun getMoviesFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = movieDao.getAllMovies()

            val moviesResult = movies.map { movie ->
                Result(
                    adult = false,
                    backdrop_path = "",
                    genre_ids = emptyList(),
                    id = 0,
                    original_language = "",
                    original_title = "",
                    overview = movie.overview,
                    popularity = 0.0,
                    poster_path = movie.poster_path,
                    release_date = "",
                    title = movie.title,
                    video = false,
                    vote_average = movie.vote_average,
                    vote_count = 0
                )
            }
            _topMovies.postValue(moviesResult)

            val moviesTheater = moviesTheaterDao.getAllMoviesTheater()
            val moviesTheaterResult = moviesTheater.map { movieTheater ->
                MovieTheater(
                    adult = false,
                    backdrop_path = "",
                    genre_ids = emptyList(),
                    id = 0,
                    original_language = "",
                    original_title = "",
                    overview = movieTheater.overview,
                    popularity = 0.0,
                    poster_path = movieTheater.poster_path,
                    release_date = "",
                    title = movieTheater.title,
                    video = false,
                    vote_average = movieTheater.vote_average,
                    vote_count = 0
                )
            }
            _moviesInTheater.postValue(moviesTheaterResult)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun filterMovies(context: Context, query: String?) {

        if (context.isNetworkAvailable()) {
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
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val filteredTopMovies = if (!query.isNullOrEmpty()) {
                    movieDao.searchMovies(query)
                } else {
                    movieDao.getAllMovies()
                }

                val moviesResult = filteredTopMovies.map { movie ->
                    Result(
                        adult = false,
                        backdrop_path = "",
                        genre_ids = emptyList(),
                        id = 0,
                        original_language = "",
                        original_title = "",
                        overview = movie.overview,
                        popularity = 0.0,
                        poster_path = movie.poster_path,
                        release_date = "",
                        title = movie.title,
                        video = false,
                        vote_average = movie.vote_average,
                        vote_count = 0
                    )
                }
                _topMovies.postValue(moviesResult)

                val filteredTheaterMovies = if (!query.isNullOrEmpty()) {
                    moviesTheaterDao.searchMoviesTheater(query)
                } else {
                    moviesTheaterDao.getAllMoviesTheater()
                }
                val moviesTheaterResult = filteredTheaterMovies.map { movieTheater ->
                    MovieTheater(
                        adult = false,
                        backdrop_path = "",
                        genre_ids = emptyList(),
                        id = 0,
                        original_language = "",
                        original_title = "",
                        overview = movieTheater.overview,
                        popularity = 0.0,
                        poster_path = movieTheater.poster_path,
                        release_date = "",
                        title = movieTheater.title,
                        video = false,
                        vote_average = movieTheater.vote_average,
                        vote_count = 0
                    )
                }
                _moviesInTheater.postValue(moviesTheaterResult)
            }
        }
    }
}