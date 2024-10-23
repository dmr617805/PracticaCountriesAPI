package com.example.practicacountriesapi.datasources

import com.example.practicacountriesapi.models.Country
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CountryApiService {
    @GET("country")
    suspend fun getAllCountries(): List<Country>

    @POST("country")
    suspend fun addCountry(@Body country: Country): Country
}