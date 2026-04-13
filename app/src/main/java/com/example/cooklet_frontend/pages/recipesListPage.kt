package com.example.cooklet_frontend.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cooklet_frontend.RecipeCard
import com.example.cooklet_frontend.api.RecipeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipePage(navController: NavController, viewModel: RecipeViewModel) {

    val recipes by viewModel.recipes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecipes()
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            FlowRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                recipes.forEach { recipe ->
                    RecipeCard(navController, recipe)
                }
            }
        }
    }

}