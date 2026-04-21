package com.example.cooklet_frontend.api

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cooklet_frontend.localStorage.preferencesStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class preferencesViewModel(
    private val context: Context
) : ViewModel() {

    private val _appPreferences = MutableStateFlow<preferences>(
        preferencesStorage.load(context)
    )

    val appPreferences: StateFlow<preferences> = _appPreferences

    private fun save() {
        preferencesStorage.save(context, _appPreferences.value)
    }

    fun changeTheme(){
        _appPreferences.value = _appPreferences.value.copy(
            lightMode = !_appPreferences.value.lightMode
        )
        save()
    }

    fun changeUnitPreference(){
        if(_appPreferences.value.unitType == measurementUnit.IMPERIAL)
            _appPreferences.value = _appPreferences.value.copy(
                unitType = measurementUnit.METRIC
            )
        else _appPreferences.value = _appPreferences.value.copy(
            unitType = measurementUnit.IMPERIAL
        )

        save()
    }

    fun changeIngredientSortType(type: ingredientSort){
        _appPreferences.value = _appPreferences.value.copy(
            ingredientSortType = type
        )
        save()
    }

    fun changeRecipeSortType(type: recipeSort){
        _appPreferences.value = _appPreferences.value.copy(
            recipeSortType = type
        )
        save()
    }
}

data class preferences (
    var lightMode: Boolean = true,
    var unitType: measurementUnit = measurementUnit.IMPERIAL,
    var ingredientSortType: ingredientSort = ingredientSort.AISLE,
    var recipeSortType: recipeSort = recipeSort.CREATED
)

enum class measurementUnit {
    METRIC, IMPERIAL
}

enum class ingredientSort {
    AISLE, NAME
}

enum class recipeSort {
    NAME, PRICE, TIME_TO_MAKE, CREATED
}

class PreferencesViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return preferencesViewModel(context.applicationContext) as T
    }
}