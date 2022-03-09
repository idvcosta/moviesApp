package com.ingrid.movieslistapp.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object ServiceFactory {
    fun createMoviesService(): MoviesService {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val language = Locale.getDefault().toLanguageTag()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalRequestUrl = originalRequest.url()

                val updatedUrl = originalRequestUrl.newBuilder()
                    .addQueryParameter("api_key", "f312161c6bec26d768aaaca97ad5a8b5")
                    .addQueryParameter("language", language)
                    .build()

                val updatedRequest = originalRequest.newBuilder().url(updatedUrl).build()
                chain.proceed(updatedRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        return retrofit.create(MoviesService::class.java)
    }
}