<<<<<<< HEAD
package com.example.backside.utils

import com.example.backside.ResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiBookService {

    @GET("books/v1/volumes?q=keyword&key=AIzaSyANIPSxWY4Yy9PrVBy46AGW84JGwX4YNf4")
    fun getBooks(): Call<BookResponse>
=======
package com.example.backside.utils

import com.example.backside.ResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiBookService {

    @GET("books/v1/volumes?q=keyword&key=AIzaSyANIPSxWY4Yy9PrVBy46AGW84JGwX4YNf4")
    fun getBooks(): Call<BookResponse>
>>>>>>> 613c4f9ab1ee8a788534ea4962eaf2f86539cbb5
}