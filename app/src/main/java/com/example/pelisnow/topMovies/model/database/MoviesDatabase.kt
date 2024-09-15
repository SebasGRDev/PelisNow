package com.example.pelisnow.topMovies.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pelisnow.topMovies.model.database.moviesintheater.MoviesTheaterDao
import com.example.pelisnow.topMovies.model.database.moviesintheater.MoviesTheaterDb
import com.example.pelisnow.topMovies.model.database.topmovies.MoviesTopDao
import com.example.pelisnow.topMovies.model.database.topmovies.MoviesTopDb

@Database(entities = [MoviesTopDb::class, MoviesTheaterDb::class], version = 2)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun movieDao(): MoviesTopDao
    abstract fun moviesTheaterDao(): MoviesTheaterDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "movies_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}