package com.example.cooklet_frontend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cooklet_frontend.models.newRecipeIngredient
import com.example.cooklet_frontend.models.newRecipeInstruction
import com.example.cooklet_frontend.models.newRecipeMeasurement
import com.example.cooklet_frontend.models.newRecipeMeasures
import com.example.cooklet_frontend.models.newRecipePayload


val defaultRecipePayload = newRecipePayload(
    title = "N/A",
    readyInMinutes = 0,
    extendedIngredients = emptyList(),
    analyzedInstructions = emptyList(),
    sourceURL = "N/A",
    image = "N/A",
    recipeTags = null,
    servings = 0,
    summary = "N/A",
    pricePerServing = 0.0,
)

@Composable
fun RecipeEditorDialog(
    initialRecipe: newRecipePayload? = null,
    onDismiss: () -> Unit,
    onSubmit: (newRecipePayload) -> Unit,
    type: String
) {
    var recipeState by remember {
        mutableStateOf(initialRecipe ?: defaultRecipePayload)
    }

    // Ensure lists are mutable copies
    val ingredients = remember {
        mutableStateListOf<newRecipeIngredient>().apply {
            addAll(recipeState.extendedIngredients)
            if (isEmpty()) add(newRecipeIngredient(
                name = "",
                id = 0,
                aisle = "",
                amount = 0.0,
                unit = "N/A",
                measures = newRecipeMeasures(
                    us = newRecipeMeasurement(
                        amount = 0.0,
                        unit = "na"
                    ),
                    metric = newRecipeMeasurement(
                        amount = 0.0,
                        unit = "na"
                    )
                )
            ))
        }
    }

    val instructions = remember {
        mutableStateListOf<newRecipeInstruction>().apply {
            addAll(recipeState.analyzedInstructions)
            if (isEmpty()) add(newRecipeInstruction(
                step = "",
                number = 1,
                ingredients = emptyList(),
                equipment = emptyList()
                ))
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("$type Recipe") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Title
                OutlinedTextField(
                    value = recipeState.title,
                    onValueChange = {
                        recipeState = recipeState.copy(title = it)
                    },
                    label = { Text("Recipe Name*") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = recipeState.image,
                    onValueChange = {
                        recipeState = recipeState.copy(image = it)
                    },
                    label = { Text("Recipe Image*") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Time
                OutlinedTextField(
                    value = recipeState.readyInMinutes.toString(),
                    onValueChange = {
                        recipeState = recipeState.copy(
                            readyInMinutes = it.toIntOrNull() ?: 0
                        )
                    },
                    label = { Text("Estimated Time (minutes)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Ingredients
                Text("Ingredients*")
                ingredients.forEachIndexed { index, ingredient ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = ingredient.name,
                            onValueChange = { newName ->
                                ingredients[index] = ingredient.copy(name = newName)
                            },
                            modifier = Modifier.weight(1f),
                            label = { Text("Ingredient ${index + 1}") }
                        )

                        IconButton(onClick = {
                            ingredients.removeAt(index)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    }
                }

                Button(onClick = {
                    ingredients.add(
                        newRecipeIngredient(
                            name = "",
                            id = 0,
                            aisle = "",
                            amount = 0.0,
                            unit = "N/A",
                            measures = newRecipeMeasures(
                                us = newRecipeMeasurement(
                                    amount = 0.0,
                                    unit = "na"
                                ),
                                metric = newRecipeMeasurement(
                                    amount = 0.0,
                                    unit = "na"
                                )
                            )
                    ))
                }) {
                    Text("Add Ingredient")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Instructions
                Text("Instructions*")
                instructions.forEachIndexed { index, step ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = step.step,
                            onValueChange = { newStep ->
                                instructions[index] = step.copy(step = newStep)
                            },
                            modifier = Modifier.weight(1f),
                            label = { Text("Step ${index + 1}") }
                        )

                        IconButton(onClick = {
                            instructions.removeAt(index)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    }
                }

                Button(onClick = {
                    instructions.add(
                        newRecipeInstruction(
                            number = instructions.size + 1,
                            step = "",
                            ingredients = emptyList(),
                            equipment = emptyList()
                        )
                    )
                }) {
                    Text("Add Step")
                }
            }
        },

        confirmButton = {
            Button(onClick = {

                val finalRecipe = recipeState.copy(
                    extendedIngredients = ingredients
                        .filter { it.name.isNotBlank() }
                        .mapIndexed { i, ing ->
                            ing.copy(
                                id = i,
                                amount = ing.amount,
                                unit = ing.unit
                            )
                        },

                    analyzedInstructions = instructions
                        .filter { it.step.isNotBlank() }
                        .mapIndexed { i, step ->
                            step.copy(number = i + 1)
                        }
                )

                onSubmit(finalRecipe)
            }) {
                Text("Save Recipe")
            }
        },

        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}