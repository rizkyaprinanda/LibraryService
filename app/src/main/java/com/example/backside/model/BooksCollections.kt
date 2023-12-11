package com.example.backside.model

data class BooksCollections(
    val author: String,
    val description: String,
    val image: String,
    val title: String,
    val uuid: String,
    val genre: String,
    val isRead: Boolean,
    val status: Int,
    val rating: Double
)
