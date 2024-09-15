package com.example.pelisnow.topMovies.model.database.topmovies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoviesTopDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)fun insertMovies(moviesTopDb: MoviesTopDb)

    @Query("SELECT * FROM MoviesTopDb")
    fun getAllMovies(): List<MoviesTopDb>

    @Query("SELECT * FROM MoviesTopDb WHERE title LIKE '%'|| :query || '%'")
    fun searchMovies(query: String): List<MoviesTopDb>

    @Update
    fun updateMovies(movie: MoviesTopDb)

    @Delete
    fun deleteMovie(movie: MoviesTopDb)
}