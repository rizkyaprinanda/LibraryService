
package com.example.backside.utils

import com.example.backside.ResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiBookService {

    @GET("books/v1/volumes?q=keyword&key=AIzaSyANIPSxWY4Yy9PrVBy46AGW84JGwX4YNf4")
    fun getBooks(): Call<BookResponse>
}