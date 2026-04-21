package com.example.cooklet_frontend.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cooklet_frontend.api.ingredientSort
import com.example.cooklet_frontend.api.preferencesViewModel
import com.example.cooklet_frontend.api.recipeSort

@Composable
fun ingredientSortDropdown(preferencesViewModel: preferencesViewModel){
    var expanded by remember { mutableStateOf(false) }
    val selectedOption = preferencesViewModel.appPreferences.collectAsState().value.ingredientSortType
    val selectedOptionText = mapOf(
        ingredientSort.AISLE to "Sort by Aisle",
        ingredientSort.NAME to "Sort by Name"
    )

    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOptionText[selectedOption]!!, fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Sort options",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(selectedOptionText[ingredientSort.AISLE]!!) },
                onClick = {
                    preferencesViewModel.changeIngredientSortType(ingredientSort.AISLE)
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(selectedOptionText[ingredientSort.NAME]!!) },
                onClick = {
                    preferencesViewModel.changeIngredientSortType(ingredientSort.NAME)
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun recipeSortDropdown(preferencesViewModel: preferencesViewModel){
    var expanded by remember { mutableStateOf(false) }
    val selectedOption = preferencesViewModel.appPreferences.collectAsState().value.recipeSortType
    val selectedOptionText = mapOf(
        recipeSort.NAME to "Sort by Name",
        recipeSort.PRICE to "Sort by Price",
        recipeSort.TIME_TO_MAKE to "Sort by Preparation Time",
        recipeSort.CREATED to "Sort by Recently Created"

    )

    Box(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOptionText[selectedOption]!!,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
                )

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Sort options",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(selectedOptionText[recipeSort.NAME]!!) },
                onClick = {
                    preferencesViewModel.changeRecipeSortType(recipeSort.NAME)
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(selectedOptionText[recipeSort.PRICE]!!) },
                onClick = {
                    preferencesViewModel.changeRecipeSortType(recipeSort.PRICE)
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(selectedOptionText[recipeSort.TIME_TO_MAKE]!!) },
                onClick = {
                    preferencesViewModel.changeRecipeSortType(recipeSort.TIME_TO_MAKE)
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(selectedOptionText[recipeSort.CREATED]!!) },
                onClick = {
                    preferencesViewModel.changeRecipeSortType(recipeSort.CREATED)
                    expanded = false
                }
            )
        }
    }
}

