package com.example.cooklet_frontend.utils

import com.example.cooklet_frontend.models.Recipe
import com.example.cooklet_frontend.models.newRecipeIngredient
import com.example.cooklet_frontend.models.newRecipeInstruction
import com.example.cooklet_frontend.models.newRecipeMeasurement
import com.example.cooklet_frontend.models.newRecipeMeasures
import com.example.cooklet_frontend.models.newRecipePayload
import com.example.cooklet_frontend.models.newRecipeTags

fun convertRecipeToPayload(recipe: Recipe?): newRecipePayload? {
    val recipe = recipe ?: return null

    val tags = newRecipeTags(
        vegetarian = recipe.recipeTags?.vegetarian ?: false,
        vegan = recipe.recipeTags?.vegan ?: false,
        glutenFree = recipe.recipeTags?.glutenFree ?: false,
        dairyFree = recipe.recipeTags?.dairyFree ?: false,
        veryHealthy = recipe.recipeTags?.veryHealthy ?: false,
        cheap = recipe.recipeTags?.cheap ?: false,
    )
    val ingredients = recipe.extendedIngredients.map { ingredient -> newRecipeIngredient(
        ingredient.id,
        ingredient.aisle,
        ingredient.name,
        ingredient.amount,
        ingredient.unit,
        newRecipeMeasures(
            us = newRecipeMeasurement(
                ingredient.measures.us.amount,
                ingredient.measures.us.unit
            ),
            metric = newRecipeMeasurement(
                ingredient.measures.metric.amount,
                ingredient.measures.metric.unit
            )
        )) }

    val instructions = recipe.analyzedInstructions.map { instruction -> newRecipeInstruction(
        number = instruction.number,
        step = instruction.step,
        ingredients = instruction.ingredients,
        equipment = instruction.equipment
    ) }

    return newRecipePayload(title = recipe.title,
        image = recipe.image,
        readyInMinutes = recipe.readyInMinutes ?: 0,
        servings = recipe.servings ?: 0,
        sourceURL = recipe.sourceURL,
        recipeTags = tags,
        pricePerServing = recipe.pricePerServing ?: 0.0,
        extendedIngredients = ingredients,
        summary = recipe.summary,
        analyzedInstructions = instructions
    )
}