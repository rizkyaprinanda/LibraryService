package com.example.backside.model

import com.google.gson.annotations.SerializedName

data class Institutions(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: String,
    @SerializedName("imgInstitution") val imgInstitution: String,
    @SerializedName("location") val location: String,
    @SerializedName("name") val name: String,
    @SerializedName("uuid") val uuid: String
)
