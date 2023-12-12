package com.example.backside.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBookClient {

    private const val BASE_URL = "https://www.googleapis.com/"
    private const val API_KEY = "AIzaSyANIPSxWY4Yy9PrVBy46AGW84JGwX4YNf4"

    val projectId = "upvotebooks" // Ganti dengan project-id Firebase Anda

    val retrofit = Retrofit.Builder()
//        .baseUrl("https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents/")
        .baseUrl("https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myApi = retrofit.create(ApiBookService::class.java)

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

}