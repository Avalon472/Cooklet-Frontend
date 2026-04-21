package com.example.cooklet_frontend.api

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cooklet_frontend.localStorage.recipeStorage
import com.example.cooklet_frontend.models.Recipe
import com.example.cooklet_frontend.models.newRecipePayload
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow("Idle")
    val state: StateFlow<String> = _state

    private val _recipes = MutableStateFlow<List<Recipe>>(recipeStorage.load(context))
    val recipes: StateFlow<List<Recipe>> = _recipes

    private fun save() {
        recipeStorage.save(context, _recipes.value)
    }

    fun fetchRecipes(){
        viewModelScope.launch {

            _state.value = "Loading..."

            try {
                val response = RetrofitInstance.api.all()

                if (response.isSuccessful) {
                    val body = response.body()

                    _recipes.value = body ?: emptyList()
                    _state.value = "Fetch success!"

                    save()

                } else {
                    _state.value = "Exception: Internal Server Error"
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
                } else {
                    _state.value = "Error Creating Recipe"
                }
            } catch (e: Exception) {
                _state.value = "Exception: ${e.message}"
            }
        }
    }

    fun deleteRecipe(id: String){
        viewModelScope.launch {
            if(id == "") return@launch
            _state.value = "Deleting Recipe..."
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

    fun editRecipe(id: String, recipe: newRecipePayload){
        viewModelScope.launch {
            if(id == "") return@launch
            _state.value = "Updating Recipe..."

            try {
                val response = RetrofitInstance.api.editRecipe(id, recipe)

                if (response.isSuccessful) {
                    fetchRecipes()
                    _state.value = "Recipe updated!"
                } else {
                    _state.value = "Internal Server Error"
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

class RecipeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeViewModel(context.applicationContext) as T
    }
}