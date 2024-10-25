package com.app.ingressocom.Model

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetroFitMovies {
    @GET("events/coming-soon/partnership/desafio")
    suspend fun getAllMovies(): Response<ApiResponse>
    companion object{
        private val retrofitServices : RetroFitMovies by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api-content.ingresso.com/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(RetroFitMovies::class.java)
        }
        fun getInstance() : RetroFitMovies {
            return retrofitServices
        }
    }
}