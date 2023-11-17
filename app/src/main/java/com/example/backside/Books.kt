package com.example.backside

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Books (
    val imgBook: Int,
    val judul: String,
    val penulis : String,
    val kategori : String,
    var jumlah : String,
    var sudahVote : Boolean,
) : Parcelable
