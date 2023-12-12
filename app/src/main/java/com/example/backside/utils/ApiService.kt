
package com.example.backside.utils

import com.example.backside.model.Books
import com.example.backside.model.BooksCollections
import com.example.backside.model.Institutions
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("institution/-Nkp_aXG-xrmzG04QveB.json")
    fun getInstitutions(): Call<List<Institutions>>

    @GET("books/-Nl6v4q6QU31wu2WSkbV.json")
    fun getBooks(): Call<List<BooksCollections>>
}