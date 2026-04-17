package com.example.cooklet_frontend.api

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cooklet_frontend.localStorage.ingredientStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.cooklet_frontend.models.Recipe

class ingredientViewModel(
    private val context: Context
) : ViewModel() {

    private val _aisleItems =
        MutableStateFlow<Map<String, List<simpleIngredient>>>(
            ingredientStorage.load(context)
        )

    val aisleItems: StateFlow<Map<String, List<simpleIngredient>>> = _aisleItems

    private fun save() {
        ingredientStorage.save(context, _aisleItems.value)
    }

    fun toggleIngredient(aisle: String, ingredientName: String, checked: Boolean) {
        _aisleItems.value = _aisleItems.value.mapValues { (key, list) ->
            if (key == aisle) {
                list.map {
                    if (it.name == ingredientName) it.copy(checked = checked)
                    else it
                }
            } else list
        }
        save()
    }

    fun addIngredients(recipe: Recipe, unit: measurementUnit) {
        val current = _aisleItems.value.toMutableMap()

        for (ingredient in recipe.extendedIngredients) {
            var aisle = ingredient.aisle ?: "Non-Standard Item(s)"
            if (aisle == "") aisle = "Non-Standard Item(s)"

            val formattedName = ingredient.name.split(" ")
                .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
            val ingredientUnit = if(unit == measurementUnit.METRIC){
                ingredient.measures.metric.unit
            }else{
                ingredient.measures.us.unit
            }
            val ingredientAmount = if(unit == measurementUnit.METRIC){
                ingredient.measures.metric.amount
            }else{
                ingredient.measures.us.amount
            }

            val list = current[aisle]?.toMutableList() ?: mutableListOf()

            val existing = list.find { it.name == formattedName && it.unit == ingredientUnit }

            if (existing != null) {
                list.replaceAll {
                    if (it.name == formattedName && it.unit == ingredientUnit)
                        it.copy(quantity = it.quantity + ingredientAmount)
                    else it
                }
            } else {
                list.add(
                    simpleIngredient(
                        name = formattedName,
                        quantity = ingredientAmount,
                        unit = ingredientUnit,
                        checked = false
                    )
                )
            }

            current[aisle] = list
        }

        _aisleItems.value = current
        save()
    }

    fun deleteChecked(){
        _aisleItems.value = _aisleItems.value
            .mapValues { (_, list) ->
                list.filter { !it.checked }
            }
            .filterValues { it.isNotEmpty() }
        save()
    }

    fun deleteAllIngredients(){
        _aisleItems.value = emptyMap()
        save()
    }
}

data class simpleIngredient (
    val name: String,
    var quantity: Double,
    val unit: String,
    val checked: Boolean
)

class IngredientViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ingredientViewModel(context.applicationContext) as T
    }
}