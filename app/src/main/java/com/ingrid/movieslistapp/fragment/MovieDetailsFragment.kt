package com.ingrid.movieslistapp.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.ingrid.movieslistapp.R
import com.ingrid.movieslistapp.databinding.FragmentMovieDetailsBinding
import com.ingrid.movieslistapp.model.Movie
import com.ingrid.movieslistapp.viewmodels.MoviesViewModel

class MovieDetailsFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MoviesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_clean, menu)
        super.onCreateOptionsMenu(menu, inflater);
        //menu.clear()
    }

    private fun initViewModels() {
        viewModel.getSelectedMovie().observe(requireActivity(), ::updateMovie)
    }

    private fun updateMovie(movie: Movie) {
        val imageURL = IMAGES_URL + movie.posterPath
        context?.let { context ->
            Glide.with(context)
                .load(imageURL)
                .into(binding.ivPosterDetail)
        }

        val scoreStr = movie.score.toString()

        binding.tvTitle.text = "Título: ${movie.title}"
        binding.tvReleaseDate.text = "Data de lançamento: ${movie.release}"
        binding.tvScore.text = "Avaliação: $scoreStr"
        binding.tvOverview.text = "Resumo: ${movie.overview}"
    }

    companion object {
        const val IMAGES_URL = "https://image.tmdb.org/t/p/w342/"
    }
}