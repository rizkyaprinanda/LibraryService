
package com.example.backside.utils

import com.example.backside.model.Institutions
import retrofit2.Call
import retrofit2.http.GET

interface ApiInstitutionService {

    @GET("institution.json")
    fun getInstitutions(): Call<List<Institutions>>
}