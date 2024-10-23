package com.example.practicacountriesapi.datasources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "https://api-countries-ka4l.onrender.com/"

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val interceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImRhdnVzZXI4OTYiLCJwYXNzd29yZCI6InRlc3QiLCJpYXQiOjE3Mjk2NTU4NDUsImV4cCI6MTc5Mjc3MTA0NX0.xuPH1UAbEfojGa8U_mdwwRQZx0nWiFj36kke7IEMv0c")
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
                                        .addInterceptor(interceptor)
                                        .connectTimeout(5, TimeUnit.MINUTES)
                                        .readTimeout(5, TimeUnit.MINUTES)
                                        .writeTimeout(5, TimeUnit.MINUTES)
                                        .build()

    val api: CountryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(CountryApiService::class.java)
    }



}