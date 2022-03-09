package com.ingrid.movieslistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ingrid.movieslistapp.databinding.RowMovieBinding
import com.ingrid.movieslistapp.model.Movie

class MoviesAdapter(private val selectMovieCallback: Consumer<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    class MovieHolder(private val movieRow: RowMovieBinding) :
        RecyclerView.ViewHolder(movieRow.root) {

        fun bind(movie: Movie) {
            movieRow.root.tag = movie

            movieRow.tvTitle.text = movie.title

            val imageURL = IMAGES_URL + movie.posterPath
            Glide.with(movieRow.root.context)
                .load(imageURL)
                .into(movieRow.ivPoster)
        }
    }

    private var movies: List<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val movieRow = RowMovieBinding.inflate(inflater, parent, false)

        movieRow.root.setOnClickListener { view ->
            val movie = view.tag as Movie
            selectMovieCallback.accept(movie)
        }
        return MovieHolder(movieRow)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        movies?.let { movies ->
            val movie = movies[position]
            holder.bind(movie)
        }
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    companion object {
        const val IMAGES_URL = "https://image.tmdb.org/t/p/w342/"
    }

}
