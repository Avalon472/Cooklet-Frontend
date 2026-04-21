package com.example.cooklet_frontend.localStorage

import android.content.Context
import com.example.cooklet_frontend.models.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object recipeStorage {

    private const val FILE_NAME = "recipes.json"

    fun save(context: Context, data: List<Recipe>) {
        val json = Gson().toJson(data)

        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun load(context: Context): List<Recipe> {
        return try {
            val json = context.openFileInput(FILE_NAME)
                .bufferedReader()
                .use { it.readText() }


            val type = object : TypeToken<List<Recipe>>() {}.type
            Gson().fromJson(json, type) ?: emptyList()

        } catch (e: Exception) {
            emptyList()
        }
    }
}