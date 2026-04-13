package com.example.cooklet_frontend.api

import androidx.lifecycle.ViewModel

class ingredientViewModel : ViewModel() {
    val aisleItems: MutableMap<String, MutableList<simpleIngredient>> = mutableMapOf()

}

data class simpleIngredient (
    val name: String,
    var quantity: Double,
    val unit: String,
//    val checked: Boolean
)