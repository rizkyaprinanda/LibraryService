<<<<<<< HEAD
package com.example.backside.utils

import com.example.backside.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBookClient {

    private const val BASE_URL = "https://www.googleapis.com/"
    private const val API_KEY = "AIzaSyANIPSxWY4Yy9PrVBy46AGW84JGwX4YNf4"

    val apiService: ApiBookService
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiBookService::class.java)
        }

=======
package com.example.backside.utils

import com.example.backside.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBookClient {

    private const val BASE_URL = "https://www.googleapis.com/"
    private const val API_KEY = "AIzaSyANIPSxWY4Yy9PrVBy46AGW84JGwX4YNf4"

    val apiService: ApiBookService
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiBookService::class.java)
        }

>>>>>>> 613c4f9ab1ee8a788534ea4962eaf2f86539cbb5
}