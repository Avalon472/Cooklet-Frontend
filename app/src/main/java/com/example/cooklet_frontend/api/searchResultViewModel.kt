package com.example.cooklet_frontend.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooklet_frontend.models.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchResultViewModel : ViewModel() {

    private val _state = MutableStateFlow("Idle")
    val searchState: StateFlow<String> = _state

    private val _searchResults = MutableStateFlow<List<Recipe>>(emptyList())
    val searchResults: StateFlow<List<Recipe>> = _searchResults

    fun searchRecipes(query: String) {
        viewModelScope.launch {

            _state.value = "Loading..."

            try {
                val response = RetrofitInstance.api.search(query)

                if (response.isSuccessful) {
                    val body = response.body()

                    _searchResults.value = body ?: emptyList()
                    _state.value = "Success (${_searchResults.value.size} results)"

                } else {
                    _state.value = "Error: ${response.code()}"
                }

            } catch (e: Exception) {
                _state.value = "Exception: ${e.message}"
            }
        }
    }

}