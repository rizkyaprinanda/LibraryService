package com.example.backside.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class BooksCollections(
    val author: String,
    val description: String,
    val image: String,
    val title: String,
    val uuid: String,
    val genre: String,
    var isVote: Boolean,
    var status: Boolean,
    var count: Int,
    val rating: Double
) : Serializable
