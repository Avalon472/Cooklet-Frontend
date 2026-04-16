package com.example.cooklet_frontend.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cooklet_frontend.ItemSortDropdown
import com.example.cooklet_frontend.api.RecipeViewModel
import com.example.cooklet_frontend.api.ingredientViewModel
import com.example.cooklet_frontend.components.IngredientItem
import com.example.cooklet_frontend.components.SearchResultsDialog


@Composable
fun IngredientsPage(
    viewModel: RecipeViewModel,
    ingredientViewModel: ingredientViewModel
){
    var showAddRecipeIngredientsModal by remember {mutableStateOf(false)}
    val aisleItems by ingredientViewModel.aisleItems.collectAsState()

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){

        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
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

                if(aisleItems.isNotEmpty()){



                    for ((key, value) in aisleItems) {
                        Text(key)
                        for (ingredient in value) {
                            IngredientItem(
                                ingredient.name,
                                ingredient.quantity,
                                ingredient.unit,
                                ingredient.checked,
                                { checked ->
                                    ingredientViewModel.toggleIngredient(key, ingredient.name, checked)
                                }
                            )
                        }
                    }
                }else{
                    Column(modifier = Modifier.fillParentMaxSize().padding(bottom = 128.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Icon(
                            Icons.AutoMirrored.Sharp.List,
                            contentDescription = null,
                            Modifier.size(128.dp))
                        Spacer(Modifier.height(32.dp))
                        Text(modifier = Modifier.fillMaxWidth(0.75f),
                            textAlign = TextAlign.Center,
                            text = "No Recipe has been added. " +
                                    "Click Add Recipe Ingredients to add one.")
                    }
                }

                if (showAddRecipeIngredientsModal) {
                    SearchResultsDialog(
                        onDismiss = { showAddRecipeIngredientsModal = false },
                        onResultSelect = { recipe ->
                            ingredientViewModel.addIngredients(recipe)
                            showAddRecipeIngredientsModal = false
                        },
                        recipeList = viewModel.recipes.collectAsState().value
                    )
                }
            }
        }

        Column(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp).fillMaxWidth(0.33f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)){
            if(aisleItems.isNotEmpty()){
                FloatingActionButton(
                    modifier = Modifier.fillMaxWidth().height(35.dp),
                    onClick = { ingredientViewModel.deleteChecked() }
                ) {
                    Text("Delete Checked")
                }
                FloatingActionButton(
                    modifier = Modifier.fillMaxWidth().height(35.dp),
                    onClick = { ingredientViewModel.deleteAllIngredients() }
                ) {
                    Text("Clear All")
                }
            }
        }
    }
}