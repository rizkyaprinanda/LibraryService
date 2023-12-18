package com.example.backside

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class PurchasedBooks(
    val img: Int,
    val judul: String,
    val penulis: String,
    val kategori: String,
    var jumlah: Int,
    var sudahVote: Boolean,
    var sudahTerbeli: Boolean,
    var deskripsi: String,
    val rating: Double,
    var lingambar : String
) : Serializable, Parcelable
