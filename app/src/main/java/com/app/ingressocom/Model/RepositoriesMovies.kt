package com.app.ingressocom.Model

import android.util.Log
import retrofit2.Response

class RepositoriesMovies(private var retroFitMovies: RetroFitMovies) {
    suspend fun getAllMovies(): Response<ApiResponse> {
        Log.d("Repository-RetroFit", "${retroFitMovies.getAllMovies()}")
        return retroFitMovies.getAllMovies()
    }
}