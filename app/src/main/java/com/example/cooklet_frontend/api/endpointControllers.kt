package com.example.cooklet_frontend.api

import com.example.cooklet_frontend.models.Recipe
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun search(
        @Query("query") query: String
    ): Response<List<Recipe>>
}

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:5000/api/recipe/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}