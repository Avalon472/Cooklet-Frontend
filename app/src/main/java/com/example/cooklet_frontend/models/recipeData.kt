package com.example.cooklet_frontend.models

data class spoonacularRecipe(
    val recipe: Recipe
)

data class Recipe(
    val id: Int?,
    val image: String,
    val title: String,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceURL: String,
    val recipeTags: Tags?,
    val pricePerServing: Double?,
    val extendedIngredients: List<Ingredient>,
    val summary: String,
    val analyzedInstructions: List<Instruction>,
    val _id: String
)
data class Tags(
    val vegetarian: Boolean = false,
    val vegan: Boolean = false,
    val glutenFree: Boolean = false,
    val dairyFree: Boolean = false,
    val veryHealthy: Boolean = false,
    val cheap: Boolean = false,
    val _id: String?
)
data class Ingredient(
    val id: Int?,
    val aisle: String?,
    val name: String,
    val amount: Double,
    val unit: String,
    val measures: Measures,
    val _id: String?
)
data class Measures(
    val us: Measurement,
    val metric: Measurement,
    val _id: String?
)

data class Measurement(
    val amount: Double,
    val unit: String,
    val _id: String?
)
data class Instruction(
    val number: Int,
    val step: String,
    val ingredients: List<String>,
    val equipment: List<String>,
    val _id: String?
)

//data class recipeQuery(
//    val query: String
//)
//
//data class queryResponse(
//    val response: List<spoonacularRecipe>
//)
//
//data class spoonacularRecipe(
//    val _id: String,
//    val id: Int,
//    val image: String,
//    val title: String,
//    val readyInMinutes: Int?,
//    val servings: Int?,
//    val sourceURL: String,
//    val recipeTags: tags,
//    val creditsText: String?,
//    val license: String?,
//    val sourceName: String?,
//    val pricePerServing: Double?,
//    val extendedIngredients: List<ingredient>,
//    val summary: String,
//    val analyzedInstructions: List<instructions>,
//    val language: String,
//    val spoonacularSourceUrl: String
//)
//
//data class tags(
//    val vegetarian: Boolean,
//    val vegan: Boolean,
//    val glutenFree: Boolean,
//    val dairyFree: Boolean,
//    val veryHealthy: Boolean,
//    val cheap: Boolean
//)
//
//data class instructions(
//    val number: Int,
//    val step: String,
//    val ingredients: List<String>,
//    val equipment: List<String>
//)
//
//data class ingredient(
//    val id: Int,
//    val aisle: String,
//    val name: String,
//    val amount: Int,
//    val unit: String,
//    val measures: measures
//)
//
//data class measures(
//    val us: usMeasurement,
//    val metric: metricMeasurement
//)
//
//data class usMeasurement(
//    val amount: Int,
//    val unit: String
//)
//
//data class metricMeasurement(
//    val amount: Int,
//    val unit: String
//)