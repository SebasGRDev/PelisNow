package com.example.pelisnow.topMovies.model.database.moviesintheater

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesTheaterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMoviesTheater(moviesTheaterDb: MoviesTheaterDb)

    @Query("SELECT * FROM MoviesTheaterDb")
    fun getAllMoviesTheater(): List<MoviesTheaterDb>

    @Query("SELECT * FROM MoviesTheaterDb WHERE title LIKE '%' || :query || '%'")
    fun searchMoviesTheater(query: String): List<MoviesTheaterDb>
}