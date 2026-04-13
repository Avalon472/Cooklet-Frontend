package com.example.cooklet_frontend.pages

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cooklet_frontend.ItemSortDropdown
import com.example.cooklet_frontend.api.RecipeViewModel
import com.example.cooklet_frontend.api.ingredientViewModel
import com.example.cooklet_frontend.api.simpleIngredient
import com.example.cooklet_frontend.components.IngredientItem
import com.example.cooklet_frontend.components.SearchResultsDialog
import com.example.cooklet_frontend.models.Recipe


@Composable
fun IngredientsPage(
    viewModel: RecipeViewModel,
    ingredientViewModel: ingredientViewModel
){
    var showAddRecipeIngredientsModal by remember {mutableStateOf(false)}
    LazyColumn (modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemSortDropdown()



                Button(onClick = { showAddRecipeIngredientsModal = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Recipe"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add Recipe Ingredients", textAlign = TextAlign.Center)
                }
            }



        for ((key, value) in ingredientViewModel.aisleItems) {
            Text(key)
            for (ingredient in value) {
                IngredientItem(ingredient.name, ingredient.quantity, ingredient.unit)
            }

        }

        if (showAddRecipeIngredientsModal) {
            SearchResultsDialog(
                onDismiss = { showAddRecipeIngredientsModal = false },
                onResultSelect = { recipe ->
                    AddIngredients(recipe, ingredientViewModel)
                    showAddRecipeIngredientsModal = false
                },
                recipeList = viewModel.recipes.collectAsState().value
            )
        }
    }
    }
}

fun AddIngredients(
    recipe: Recipe,
    viewModel: ingredientViewModel
){
    for (ingredient in recipe.extendedIngredients) {
        //Add under specified aisle, otherwise group into catch-all aisle
        var aisle = ingredient.aisle ?: "Non-Standard Item(s)"
        if(aisle == "") aisle = "Non-Standard Item(s)"

        val list = viewModel.aisleItems.getOrPut(aisle) { mutableListOf() }

        val existing = list.find { it.name == ingredient.name }

        if (existing != null) {
            existing.quantity += ingredient.amount
        } else {
            list.add(
                simpleIngredient(
                    //Splitting and mutating string to capitalize first letter of each word
                    name = ingredient.name.split(" ").joinToString(" ") { word ->
                        word.replaceFirstChar { it.uppercase() }
                    },
                    quantity = ingredient.amount,
                    unit = ingredient.unit,
//                    checked = false
                )
            )
        }
    }
}

@Composable
fun AddRecipeIngredientsDialog(
    viewModel: RecipeViewModel,
    onDismiss: () -> Unit,
    onResultSelect: (Recipe) -> Unit
){

    var selectedRecipe: Recipe? by remember {
        mutableStateOf(null)
    }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Recipe") },
        text = {
            LazyColumn(modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()) {
                items(
                    viewModel.recipes.value
                ){result ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { selectedRecipe = result },
                        colors = CardDefaults.cardColors(
                            containerColor = if (result == selectedRecipe)
                                Color(0xFFE0E0E0)
                            else
                                Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        AsyncImage(
                            model = result.image,
                            contentDescription = result.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text=result.title, fontSize = 20.sp, fontWeight= FontWeight.Bold)

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("Price Per Serving: $${"%.2f".format(result.pricePerServing ?: 0.0)}")
                            Text("⏱ ${result.readyInMinutes ?: 0} min")
                        }
                    }

                }
            }
        },


        confirmButton = {
            Button(
                onClick = {
                    val recipe = selectedRecipe

                    if (recipe == null) {
                        Toast.makeText(
                            context,
                            "Please select a recipe",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        onResultSelect(recipe)
                    }
                }
            ) {
                Text("Confirm")
            }
        }
    )
}