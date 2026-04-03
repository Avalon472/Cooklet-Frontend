package com.example.cooklet_frontend.models

data class newRecipePayload (
    val image: String,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceURL: String,
    val recipeTags: newRecipeTags?,
    val pricePerServing: Double,
    val extendedIngredients: List<newRecipeIngredient>,
    val summary: String,
    val analyzedInstructions: List<newRecipeInstruction>,
)
data class newRecipeTags(
    val vegetarian: Boolean = false,
    val vegan: Boolean = false,
    val glutenFree: Boolean = false,
    val dairyFree: Boolean = false,
    val veryHealthy: Boolean = false,
    val cheap: Boolean = false,
)
data class newRecipeIngredient(
    val id: Int?,
    val aisle: String?,
    val name: String,
    val amount: Double,
    val unit: String,
    val measures: newRecipeMeasures,
)
data class newRecipeMeasures(
    val us: newRecipeMeasurement?,
    val metric: newRecipeMeasurement?,
)

data class newRecipeMeasurement(
    val amount: Double,
    val unit: String,
)
data class newRecipeInstruction(
    val number: Int,
    val step: String,
    val ingredients: List<String>,
    val equipment: List<String>,
)