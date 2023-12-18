package com.example.backside.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class BooksCollections(
    val author: String = "",
    val description: String = "",
    val image: String = "",
    val title: String = "",
    val uuid: String = "",
    val genre: String = "",
    var isVote: Boolean = false,
    var status: Boolean = false,
    var count: Int = 0,
    val rating: Double = 0.0
) : Serializable
