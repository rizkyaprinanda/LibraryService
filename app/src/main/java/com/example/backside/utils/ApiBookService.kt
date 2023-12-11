
package com.example.backside.utils

import com.example.backside.model.BooksCollections
import retrofit2.Call
import retrofit2.http.GET

public interface ApiBookService {

    @GET("books/v1/volumes?q=keyword&key=AIzaSyANIPSxWY4Yy9PrVBy46AGW84JGwX4YNf4")
    fun getBooks(): Call<BookResponse>

    @GET("books")
    fun getCollectionData(): Call<List<BooksCollections?>?>?
}