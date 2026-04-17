package com.example.cooklet_frontend.localStorage

import android.content.Context
import com.example.cooklet_frontend.api.preferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object preferencesStorage {

    private const val FILE_NAME = "preferences.json"

    fun save(context: Context, data: preferences) {
        val json = Gson().toJson(data)

        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun load(context: Context): preferences {
        return try {
            val json = context.openFileInput(FILE_NAME)
                .bufferedReader()
                .use { it.readText() }


            val type = object : TypeToken<preferences>() {}.type
            Gson().fromJson(json, type) ?: preferences()

        } catch (e: Exception) {
            preferences()
        }
    }
}