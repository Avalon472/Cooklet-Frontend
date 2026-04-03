package com.example.cooklet_frontend.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooklet_frontend.models.Recipe
import com.example.cooklet_frontend.models.newRecipePayload
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val defaultNewRecipe = newRecipePayload(image = "https://example.com",
        title = "N/A",
        readyInMinutes = 0,
        servings = 0,
        sourceURL = "N/A",
        recipeTags = null,
        pricePerServing = 0.0,
        extendedIngredients = emptyList(),
        summary = "N/A",
        analyzedInstructions = emptyList()
        )

    private val _state = MutableStateFlow("Idle")
    val state: StateFlow<String> = _state

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

//    private val _newRecipe = MutableStateFlow(newRecipePayload(
//        image = "https://example.com",
//        title = "N/A",
//        readyInMinutes = 0,
//        servings = 0,
//        sourceURL = "N/A",
//        recipeTags = null,
//        pricePerServing = 0.0,
//        extendedIngredients = emptyList(),
//        summary = "N/A",
//        analyzedInstructions = emptyList()
//    ))
//
//    val newRecipe: StateFlow<newRecipePayload> = _newRecipe

    fun searchRecipes(query: String) {
        viewModelScope.launch {

            _state.value = "Loading..."

            try {
                val response = RetrofitInstance.api.search(query)

                if (response.isSuccessful) {
                    val body = response.body()

                    _recipes.value = body ?: emptyList()
                    _state.value = "Success (${_recipes.value.size} results)"

                } else {
                    _state.value = "Error: ${response.code()}"
                }

            } catch (e: Exception) {
                _state.value = "Exception: ${e.message}"
            }
        }
    }

    fun fetchRecipes(){
        viewModelScope.launch {

            _state.value = "Loading..."

            try {
                val response = RetrofitInstance.api.all()

                if (response.isSuccessful) {
                    val body = response.body()

                    _recipes.value = body ?: emptyList()
                    _state.value = "Success (${_recipes.value.size} results)"

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
}