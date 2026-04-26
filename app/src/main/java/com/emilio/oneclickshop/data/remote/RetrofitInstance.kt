package com.emilio.oneclickshop.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitInstance is a SINGLETON — meaning only ONE copy exists in the entire app.
 * Think of it as the ONE phone we use to call the restaurant.
 * We don't need 10 phones — just one is enough.
 *
 * 'object' in Kotlin automatically makes this a singleton.
 */
object RetrofitInstance {

    // The base URL of our API — all endpoint paths are added after this
    private const val BASE_URL = "https://oneclick.runasp.net/"

    /**
     * HttpLoggingInterceptor lets us SEE all API calls in Android Studio's Logcat.
     * Very useful for debugging — like having a security camera on our mesero (waiter).
     * We only log in DEBUG mode to keep production clean.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * OkHttpClient is the actual engine that sends HTTP requests.
     * Retrofit sits on top of it and makes it easier to use.
     * We attach the logging interceptor here so every request is logged.
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * This is the actual Retrofit instance.
     * 'lazy' means it is only created the FIRST TIME it is needed — not before.
     * Like a lazy person who only gets up when absolutely necessary 😄
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // converts JSON → Kotlin objects
            .build()
    }

    /**
     * This is the ApiService instance we use throughout the app.
     * Call RetrofitInstance.api.getProducts() anywhere to fetch products.
     */
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}


