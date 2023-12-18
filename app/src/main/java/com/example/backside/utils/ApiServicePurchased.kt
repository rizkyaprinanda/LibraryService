package com.example.backside.utils
import com.example.backside.PurchasedBooks
import com.example.backside.model.Books
import retrofit2.Call
import retrofit2.http.GET
interface ApiServicePurchased {
    @GET("data")
    fun getData(): Call<List<PurchasedBooks>>
}