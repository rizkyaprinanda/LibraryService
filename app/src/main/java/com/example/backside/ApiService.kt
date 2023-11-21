
package com.example.backside

import com.example.backside.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("popular?api_key=c91b07124c855f7577ce56962639cd93")
    fun getPopularMovies(): Call<ResponseModel>

}

