package com.example.cooklet_frontend.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.cooklet_frontend.models.Recipe
import com.example.cooklet_frontend.room.AisleEntity
import com.example.cooklet_frontend.room.IngredientDao
import com.example.cooklet_frontend.room.IngredientEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ingredientViewModel(
    private val dao: IngredientDao
) : ViewModel() {

    val aisleItems: StateFlow<Map<String, List<IngredientEntity>>> =
        dao.getAislesWithIngredients()
            .map { list ->
                list.associate { aisleWithIngredients ->
                    aisleWithIngredients.aisle.name to
                            aisleWithIngredients.ingredients
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyMap())

    fun toggleIngredient(ingredient: IngredientEntity, checked: Boolean) {
        viewModelScope.launch {
            dao.updateIngredient(ingredient.copy(checked = checked))
        }
    }

    fun addIngredients(recipe: Recipe) {
        viewModelScope.launch {
            for (ingredient in recipe.extendedIngredients) {

                var aisle = ingredient.aisle ?: "Non-Standard Item(s)"
                if (aisle.isBlank()) aisle = "Non-Standard Item(s)"

                val formattedName = ingredient.name.split(" ")
                    .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

                dao.insertAisle(AisleEntity(aisle))

                dao.insertOrIncrement(
                    name = formattedName,
                    quantity = ingredient.amount,
                    unit = ingredient.unit,
                    aisleName = aisle
                )
            }
        }
    }

    fun deleteChecked() {
        viewModelScope.launch {
            dao.deleteChecked()
        }
    }

    fun deleteAllIngredients(){
        viewModelScope.launch {
            dao.deleteAllIngredients()
        }
    }
}