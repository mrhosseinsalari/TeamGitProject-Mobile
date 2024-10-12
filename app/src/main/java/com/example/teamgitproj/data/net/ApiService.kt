package com.example.teamgitproj.data.net

import com.example.teamgitproj.data.model.BlogResponse
import com.example.teamgitproj.util.SITE_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("blog")
    suspend fun getBlogs() : BlogResponse
}

fun createApiService() :ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(SITE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create( ApiService::class.java )
}