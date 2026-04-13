package com.example.cooklet_frontend.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cooklet_frontend.api.RecipeViewModel

@Composable
fun RecipeDetailsPage(recipeId: String?, viewModel: RecipeViewModel){

    val recipes by viewModel.recipes.collectAsState()

    LaunchedEffect(Unit) {
        if (recipes.isEmpty()) {
            viewModel.fetchRecipes()
        }
    }

    val recipe = recipes.find { it._id == recipeId }

    if (recipe == null) {
        Text("Recipe not found or loading...")
        return
    }

    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(15.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)) {

        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Text(
            recipe.title,
            fontSize = 28.sp,
            textDecoration = TextDecoration.Underline
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("${recipe.readyInMinutes ?: 0} Minutes")
            Icon(Icons.Filled.Timer, contentDescription = null)
        }

        Text("Ingredients", fontSize = 25.sp, textDecoration = TextDecoration.Underline)

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            recipe.extendedIngredients.forEach {
                Text("- ${it.amount} ${it.unit} ${it.name}")
            }
        }

        Text("Instructions", fontSize = 25.sp, textDecoration = TextDecoration.Underline)

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            recipe.analyzedInstructions.forEach {
                Text("${it.number}. ${it.step}")
            }
        }

    }
}