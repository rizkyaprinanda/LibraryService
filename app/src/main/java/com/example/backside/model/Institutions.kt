package com.example.backside.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Institutions (
    val id: String,
    val imgInstitution: Int,
    val name: String,
    val location: String,
    val description: String,
    val accessToken: String,
) : Parcelable