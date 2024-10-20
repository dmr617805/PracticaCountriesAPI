package com.example.practicacountriesapi.datasources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://157.56.166.34:8080/api/"

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    val api: CountryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(CountryApiService::class.java)
    }



}