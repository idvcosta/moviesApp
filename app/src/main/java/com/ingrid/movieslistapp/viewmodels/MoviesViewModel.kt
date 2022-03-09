package com.ingrid.movieslistapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingrid.movieslistapp.api.ServiceFactory
import com.ingrid.movieslistapp.model.Movie
import com.ingrid.movieslistapp.model.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    private val status = MutableLiveData(Status.LOADING)
    private val movies = MutableLiveData<List<Movie>>()
    private val selectedMovie = MutableLiveData<Movie>()

    init {
        requestMovies()
    }

    fun getStatus(): LiveData<Status> = status
    fun getMovies(): LiveData<List<Movie>> = movies
    fun getSelectedMovie(): LiveData<Movie> = selectedMovie

    private fun requestMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val service = ServiceFactory.createMoviesService()
            val listMoviesCall = service.listMovies()

            listMoviesCall.enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    response.body()?.let { moviesResponse ->
                        this@MoviesViewModel.movies.postValue(moviesResponse.results)
                    }
                    status.postValue(Status.SUCCESS)
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    Log.e(TAG, "Error loading movies", t)
                    status.postValue(Status.FAILURE)
                }
            })
        }
    }

    fun selectMovie(movie: Movie) {
        selectedMovie.postValue(movie)
    }

    fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val service = ServiceFactory.createMoviesService()
            val searchMovieCall = service.searchMovies(query)

            searchMovieCall.enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    response.body()?.let { moviesResponse ->
                        this@MoviesViewModel.movies.postValue(moviesResponse.results)
                    }
                    status.postValue(Status.SUCCESS)
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    Log.e(TAG, "Error loading movies", t)
                    status.postValue(Status.FAILURE)
                }
            })
        }
    }

    companion object {
        val TAG = MoviesViewModel.javaClass.toString()
    }
}


