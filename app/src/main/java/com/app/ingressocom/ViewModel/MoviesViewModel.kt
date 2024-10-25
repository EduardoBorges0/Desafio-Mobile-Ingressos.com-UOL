package com.app.ingressocom.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ingressocom.Model.ApiResponse
import com.app.ingressocom.Model.Movie
import com.app.ingressocom.Model.RepositoriesMovies
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel(private var repositoriesMovies: RepositoriesMovies): ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>> get() = _moviesList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        viewModelScope.launch {
            getAllMovies()
        }
    }

    private suspend fun getAllMovies() {
        _isLoading.value = true
        val response: Response<ApiResponse> = repositoriesMovies.getAllMovies()
        if (response.isSuccessful) {
            _moviesList.value = response.body()?.items
            Log.d("MovieList", "$_moviesList")

        }
        _isLoading.value = false
    }
}
