package com.example.pelisnow.topMovies.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisnow.R
import com.example.pelisnow.databinding.FragmentMovieBinding
import com.example.pelisnow.topMovies.utils.isNetworkAvailable
import com.example.pelisnow.topMovies.view.adapters.theatermovies.MoviesInTheaterAdapter
import com.example.pelisnow.topMovies.view.adapters.topmovies.TopMoviesAdapter
import com.example.pelisnow.topMovies.viewmodel.MovieViewModel


class MovieFragment : Fragment(R.layout.fragment_movie) {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var viewModel: MovieViewModel

    private lateinit var recyclerViewTopMovies: RecyclerView
    private lateinit var recyclerViewMoviesInTheater: RecyclerView

    val topMoviesAdapter = TopMoviesAdapter(emptyList())
    val moviesTheaterAdapter = MoviesInTheaterAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(MovieViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().isNetworkAvailable()){
            viewModel.fetchMovies(getString(R.string.apy_key))
        } else {
            viewModel.getMoviesFromDatabase()
        }

        searchTopMovie()
        setRecyclerViewTopMovies(view)
        setRecyclerViewMoviesInTheater(view)

        viewModel.topMovies.observe(viewLifecycleOwner) { movies ->
            topMoviesAdapter.topMovies = movies
            topMoviesAdapter.notifyDataSetChanged()
        }
        viewModel.moviesInTheater.observe(viewLifecycleOwner) { movies ->
            moviesTheaterAdapter.theaterMovies = movies
            moviesTheaterAdapter.notifyDataSetChanged()
        }

    }

    private fun searchTopMovie() {
        binding.searchBarView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterMovies(requireContext(), newText)
                return true
            }
        })
    }

    private fun setRecyclerViewMoviesInTheater(view: View) {
        recyclerViewMoviesInTheater = view.findViewById(R.id.recyclerView_movies_in_theaters)
        recyclerViewMoviesInTheater.setHasFixedSize(true)

        recyclerViewMoviesInTheater.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        recyclerViewMoviesInTheater.adapter = moviesTheaterAdapter
    }

    private fun setRecyclerViewTopMovies(view: View) {
        recyclerViewTopMovies = view.findViewById(R.id.recyclerView_topMovies)
        recyclerViewTopMovies.setHasFixedSize(true)

        recyclerViewTopMovies.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        recyclerViewTopMovies.adapter = topMoviesAdapter
    }
}


