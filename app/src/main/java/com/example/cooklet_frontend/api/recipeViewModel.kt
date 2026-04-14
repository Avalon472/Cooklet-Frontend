package com.example.cooklet_frontend.api

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooklet_frontend.models.Recipe
import com.example.cooklet_frontend.models.newRecipePayload
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _state = MutableStateFlow("Idle")
    val state: StateFlow<String> = _state

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    fun fetchRecipes(){
        viewModelScope.launch {

            _state.value = "Loading..."

            try {
                val response = RetrofitInstance.api.all()

                if (response.isSuccessful) {
                    val body = response.body()

                    _recipes.value = body ?: emptyList()
                    _state.value = "Success (${_recipes.value.size} results)"
                    Log.d("SEARCH_DEBUG", _state.value)

                } else {
                    _state.value = "Error: ${response.code()}"
                }

            } catch (e: Exception) {
                _state.value = "Exception: ${e.message}"
            }
        }
    }

    fun createRecipe(recipe: newRecipePayload) {
        viewModelScope.launch {

            _state.value = "Creating Recipe..."

            try {
                val response = RetrofitInstance.api.createRecipe(recipe)

                if (response.isSuccessful) {
                    _state.value = "Recipe created!"

                    fetchRecipes()
                } else {
                    _state.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _state.value = "Exception: ${e.message}"
            }
        }
    }

    fun deleteRecipe(id: String){
        viewModelScope.launch {
            if(id == "") return@launch
            try {
                val response = RetrofitInstance.api.deleteRecipe(id)

                if (response.isSuccessful) {
                    _state.value = "Recipe deleted!"
                } else {
                    _state.value = "Exception: Internal Server Error"
                }

            } catch (e: Exception) {
                _state.value = "Exception: ${e.message}"
            }
        }
    }

    fun resetState(){
        _state.value = "Idle"
    }
}