package com.example.cooklet_frontend.api

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.cooklet_frontend.models.Recipe

class ingredientViewModel : ViewModel() {

    private val _aisleItems =
        MutableStateFlow<Map<String, List<simpleIngredient>>>(emptyMap())

    val aisleItems: StateFlow<Map<String, List<simpleIngredient>>> = _aisleItems

    fun toggleIngredient(aisle: String, ingredientName: String, checked: Boolean) {
        _aisleItems.value = _aisleItems.value.mapValues { (key, list) ->
            if (key == aisle) {
                list.map {
                    if (it.name == ingredientName) it.copy(checked = checked)
                    else it
                }
            } else list
        }
    }

    fun addIngredients(recipe: Recipe) {
        val current = _aisleItems.value.toMutableMap()

        for (ingredient in recipe.extendedIngredients) {
            var aisle = ingredient.aisle ?: "Non-Standard Item(s)"
            if (aisle == "") aisle = "Non-Standard Item(s)"

            val formattedName = ingredient.name.split(" ")
                .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

            val list = current[aisle]?.toMutableList() ?: mutableListOf()

            val existing = list.find { it.name == formattedName }

            if (existing != null) {
                list.replaceAll {
                    if (it.name == formattedName)
                        it.copy(quantity = it.quantity + ingredient.amount)
                    else it
                }
            } else {
                list.add(
                    simpleIngredient(
                        name = formattedName,
                        quantity = ingredient.amount,
                        unit = ingredient.unit,
                        checked = false
                    )
                )
            }

            current[aisle] = list
        }

        _aisleItems.value = current
    }

    fun deleteChecked(){
        _aisleItems.value = _aisleItems.value
            .mapValues { (_, list) ->
                list.filter { !it.checked }
            }
            .filterValues { it.isNotEmpty() }
    }

    fun deleteAllIngredients(){
        _aisleItems.value = emptyMap()
    }
}

data class simpleIngredient (
    val name: String,
    var quantity: Double,
    val unit: String,
    val checked: Boolean
)