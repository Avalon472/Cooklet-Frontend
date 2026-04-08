package com.example.cooklet_frontend.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.cooklet_frontend.api.SearchResultViewModel

@Composable
fun SearchRecipeDialog(
    onDismiss: () -> Unit,
    onSearch: () -> Unit,
    viewModel: SearchResultViewModel
) {
    var query by remember { mutableStateOf("")}

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                // input validation and save recipe TODO
//                val fakeRecipe = Recipe(
//                    id = null,
//                    image = "",
//                    title = "Test Recipe",
//                    readyInMinutes = 30,
//                    servings = null,
//                    sourceURL = "",
//                    recipeTags = null,
//                    pricePerServing = null,
//                    extendedIngredients = emptyList(),
//                    summary = "Sample summary",
//                    analyzedInstructions = emptyList(),
//                    _id = null
//                )


//                onSearch(fakeRecipe)
                viewModel.searchRecipes(query)
                onSearch()
            }) {
                Text("Search Spoonacular")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Search for a Recipe") },
        text = {
            Column {

                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Recipe"
                    ) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}