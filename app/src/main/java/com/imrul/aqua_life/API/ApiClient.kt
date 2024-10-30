package com.imrul.aqua_life.API

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
//    private const val BASE_URL = "https://dummyjson.com/"
    private const val BASE_URL = "https://api-test.aqua-global.zone/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient with logging interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Create a custom Gson instance with lenient parsing
    private val gson = GsonBuilder()
        .setLenient()  // Enable lenient parsing
        .create()

    // Retrofit instance with the custom Gson
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))  // Use the custom Gson here
        .build()

    val authService: AuthService = retrofit.create(AuthService::class.java)
}
