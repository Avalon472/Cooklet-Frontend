package com.example.cooklet_frontend.pages

import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardReturn
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cooklet_frontend.api.RecipeViewModel
import com.example.cooklet_frontend.components.ConfirmDeleteModal
import com.example.cooklet_frontend.components.RecipeEditorDialog
import com.example.cooklet_frontend.utils.convertRecipeToPayload

@Composable
fun RecipeDetailsPage(recipeId: String, viewModel: RecipeViewModel, navController: NavController){

    val recipes by viewModel.recipes.collectAsState()
    val recipe = recipes.find { it._id == recipeId }

    val recipeState by viewModel.state.collectAsState()
    var showDeleteDialog by remember {mutableStateOf(false)}
    var showEditorDialog by remember {mutableStateOf(false)}
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (recipes.isEmpty()) {
            viewModel.fetchRecipes()
        }
    }

    LaunchedEffect(recipeState) {
        if(recipeState == "Recipe deleted!") {
            navController.popBackStack()
            viewModel.resetState()
            Toast.makeText(
                context,
                "${recipe!!.title} has been deleted!",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if(recipeState == "Recipe updated!"){
            viewModel.resetState()
            Toast.makeText(
                context,
                "${recipe!!.title} has been updated!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }


            //Control buttons for edit and delete
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { showEditorDialog = true }) {
                    Icon(Icons.Filled.Edit, contentDescription = null)
                }

                Button(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = {
                        showDeleteDialog = true;
                    }) {
                    Icon(Icons.Filled.Delete, contentDescription = null)
                }
            }
        }

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

    if(showDeleteDialog){
        ConfirmDeleteModal(
            onDismiss = {
            showDeleteDialog = false;
            },
            onConfirm = {
            showDeleteDialog = false;
            viewModel.deleteRecipe(recipe._id ?: "")
            })
    }

    if (showEditorDialog) {
        RecipeEditorDialog(
            initialRecipe = convertRecipeToPayload(recipe),
            onDismiss = { showEditorDialog = false },
            onSubmit = {
                viewModel.editRecipe(recipeId, it)
                showEditorDialog = false
            },
            type = "Edit"
        )
    }
}