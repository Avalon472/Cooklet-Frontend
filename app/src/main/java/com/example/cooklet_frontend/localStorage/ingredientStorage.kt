package com.example.cooklet_frontend.localStorage

import android.content.Context
import com.example.cooklet_frontend.api.simpleIngredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ingredientStorage {

    private const val FILE_NAME = "ingredients.json"

    fun save(context: Context, data: Map<String, List<simpleIngredient>>) {
        val json = Gson().toJson(data)

        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun load(context: Context): Map<String, List<simpleIngredient>> {
        return try {
            val json = context.openFileInput(FILE_NAME)
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<Map<String, List<simpleIngredient>>>() {}.type
            Gson().fromJson(json, type) ?: emptyMap()

        } catch (e: Exception) {
            emptyMap()
        }
    }
}