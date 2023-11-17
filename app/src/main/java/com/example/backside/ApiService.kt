<<<<<<< HEAD
package com.example.backside

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("popular?api_key=c91b07124c855f7577ce56962639cd93")
    fun getPopularMovies(): Call<ResponseModel>

}
=======
package com.example.backside

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("popular?api_key=c91b07124c855f7577ce56962639cd93")
    fun getPopularMovies(): Call<ResponseModel>

}
>>>>>>> 613c4f9ab1ee8a788534ea4962eaf2f86539cbb5
