package com.example.pelisnow.topMovies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisnow.R
import com.example.pelisnow.topMovies.model.data.MovieClient
import com.example.pelisnow.topMovies.view.adapters.theatermovies.MoviesInTheaterAdapter
import com.example.pelisnow.topMovies.view.adapters.topmovies.TopMoviesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieFragment : Fragment(R.layout.fragment_movie) {

    private lateinit var recyclerViewTopMovies: RecyclerView
    private lateinit var recyclerViewMoviesInTheater: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewTopMovies(view)
        setRecyclerViewMoviesInTheater(view)
    }

    private fun setRecyclerViewMoviesInTheater(view: View) {
        recyclerViewMoviesInTheater = view.findViewById(R.id.recyclerView_movies_in_theaters)
        recyclerViewMoviesInTheater.setHasFixedSize(true)

        recyclerViewMoviesInTheater.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        val moviesTheaterAdapter = MoviesInTheaterAdapter(emptyList())
        recyclerViewMoviesInTheater.adapter = moviesTheaterAdapter

        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = getString(R.string.apy_key)
            val moviesTheater = MovieClient.service.listMoviesInTheaters(apiKey)
            val body = moviesTheater.execute().body()

            CoroutineScope(Dispatchers.Main).launch {
                if (body != null) {
                    moviesTheaterAdapter.theaterMovies = body.moviesinTheater
                    moviesTheaterAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setRecyclerViewTopMovies(view: View){
        recyclerViewTopMovies = view.findViewById(R.id.recyclerView_topMovies)
        recyclerViewTopMovies.setHasFixedSize(true)

        recyclerViewTopMovies.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        val topMoviesAdapter = TopMoviesAdapter(emptyList())
        recyclerViewTopMovies.adapter = topMoviesAdapter

        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = getString(R.string.apy_key)
            val topMovies = MovieClient.service.listTopMovies(apiKey)
            val body = topMovies.execute().body()

            CoroutineScope(Dispatchers.Main).launch {
                if (body != null) {
                    topMoviesAdapter.topMovies = body.results
                    topMoviesAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}


