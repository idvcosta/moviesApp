package com.ingrid.movieslistapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ingrid.movieslistapp.adapter.MoviesAdapter
import com.ingrid.movieslistapp.databinding.FragmentMoviesListBinding
import com.ingrid.movieslistapp.model.Movie
import com.ingrid.movieslistapp.viewmodels.MoviesViewModel

class MoviesListFragment : Fragment() {

    lateinit var binding: FragmentMoviesListBinding
    private val moviesAdapter = MoviesAdapter(::onMovieSelected)

    private val viewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        binding.rvMovies.adapter = moviesAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getMovies().observe(requireActivity(), ::updateMovies)
    }

    private fun updateMovies(movies: List<Movie>) {
        moviesAdapter.updateMovies(movies)
    }

    private fun onMovieSelected(movie: Movie) {
        viewModel.selectMovie(movie)
    }
}
