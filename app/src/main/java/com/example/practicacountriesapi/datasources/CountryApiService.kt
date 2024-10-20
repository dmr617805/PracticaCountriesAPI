package com.example.practicacountriesapi.datasources

import com.example.practicacountriesapi.models.Country
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryApiService {
    @GET("Country")
    suspend fun getAllCountries(): List<Country>

}