package com.ingrid.movieslistapp.api

import retrofit2.http.GET
import com.ingrid.movieslistapp.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.Query

//https://api.themoviedb.org/3/discover/movie?api_key=f312161c6bec26d768aaaca97ad5a8b5
interface MoviesService {
    @GET("discover/movie?api_key=f312161c6bec26d768aaaca97ad5a8b5")
    fun listMovies(): Call<MoviesResponse>

    @GET("search/movie?page=1&api_key=f312161c6bec26d768aaaca97ad5a8b5")
    fun searchMovies(@Query("query") query: String): Call<MoviesResponse>
}