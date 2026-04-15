package com.example.cooklet_frontend.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoFood
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cooklet_frontend.api.RecipeViewModel
import com.example.cooklet_frontend.components.RecipeCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipePage(navController: NavController, viewModel: RecipeViewModel) {

    val recipes by viewModel.recipes.collectAsState()

    LaunchedEffect(Unit) {
//        viewModel.fetchRecipes()
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
                if(recipes.isNotEmpty()) {
                    recipes.forEach { recipe ->
                        RecipeCard(navController, recipe)
                    }
                }else {
                    Column(modifier = Modifier.fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                    Icon(Icons.Filled.NoFood, contentDescription = null,
                        Modifier.size(128.dp))
                    Spacer(Modifier.height(32.dp))
                    Text(
                        "No recipe has been created yet. " +
                                "Head over to the create tab to make your first recipe!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.75f)
                    )
                    }
                }
            }
        }
    }

}