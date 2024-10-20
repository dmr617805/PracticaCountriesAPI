package com.example.practicacountriesapi.repositories

import com.example.practicacountriesapi.datasources.RetrofitInstance
import com.example.practicacountriesapi.models.Country

class CountryRepository {
    private val api = RetrofitInstance.api

    suspend fun getCountries():List<Country>{
        val result = api.getAllCountries()
        return  result
    }
}