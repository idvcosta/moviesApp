package com.ingrid.movieslistapp.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val title: String,
    val overview: String,
    @SerializedName("release_date")
    val release: String,
    @SerializedName("vote_average")
    val score: Double,
    @SerializedName("poster_path")
    val posterPath: String
)