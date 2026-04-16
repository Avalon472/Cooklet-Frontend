package com.example.cooklet_frontend.api

import com.example.cooklet_frontend.models.Recipe
import com.example.cooklet_frontend.models.newRecipePayload
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun search(
        @Query("query") query: String
    ): Response<List<Recipe>>

    @GET("all")
    suspend fun all(
    ): Response<List<Recipe>>

    @POST("create")
    suspend fun createRecipe(
        @Body recipe: newRecipePayload
    ): Response<Recipe>

    @DELETE("{id}")
    suspend fun deleteRecipe(@Path("id") id: String): Response<Unit>

    @PUT("{id}")
    suspend fun editRecipe(
        @Path("id") id:String,
        @Body recipe: newRecipePayload
    ): Response<Recipe>
}

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:5000/api/recipe/"
//    private const val BASE_URL = "http://10.13.34.33:5000/api/recipe/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}