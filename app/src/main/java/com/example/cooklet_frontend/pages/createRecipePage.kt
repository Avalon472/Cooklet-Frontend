package com.example.cooklet_frontend.pages

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cooklet_frontend.api.RecipeViewModel
import com.example.cooklet_frontend.api.SearchResultViewModel
import com.example.cooklet_frontend.components.RecipeEditorDialog
import com.example.cooklet_frontend.components.SearchRecipeDialog
import com.example.cooklet_frontend.components.SearchResultsDialog
import com.example.cooklet_frontend.models.Recipe
import com.example.cooklet_frontend.utils.convertRecipeToPayload

@Composable
fun CreatePage(viewModel: RecipeViewModel, searchModel: SearchResultViewModel){

    var showEditorDialog by remember { mutableStateOf(false)}
    var showSearchDialog by remember {mutableStateOf(false)}
    var showSearchResultDialog by remember {mutableStateOf(false)}
    var creatingRecipe by remember {mutableStateOf(false)}

    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
    val recipeState by viewModel.state.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(recipeState) {
        if(recipeState == "Recipe created!") {
            viewModel.resetState()
            creatingRecipe = false;
            Toast.makeText(
                context,
                "${selectedRecipe?.title} has been created!",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if(recipeState == "Creating Recipe..."){
            creatingRecipe = true;
        }
        else if(recipeState == "Error Creating Recipe"){
            viewModel.resetState()
            Toast.makeText(
                context,
                "An error occurred while creating your recipe." +
                        "Please confirm your internet connection and try again.",
                Toast.LENGTH_SHORT
            ).show()
            showEditorDialog = true;
            creatingRecipe = false;
        }
        else if(recipeState != "Idle" && recipeState != "Loading..." && recipeState != "Fetch success!"){
            viewModel.resetState()
            Toast.makeText(
                context,
                "Failed to search for recipes. " +
                        "Please confirm your internet connection and try again.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Recipe",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = {
                selectedRecipe = null
                showEditorDialog = true
            },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Manually")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { showSearchDialog = true },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Recipe")
        }
    }

    if (showEditorDialog) {
        RecipeEditorDialog(
            initialRecipe = convertRecipeToPayload(selectedRecipe),
            onDismiss = { showEditorDialog = false },
            onSubmit = {
                viewModel.createRecipe(it)
                showEditorDialog = false
            },
            type = "Create",
            showLoading = creatingRecipe
        )
    }
    if (showSearchDialog) {
        SearchRecipeDialog(
            onDismiss = { showSearchDialog = false },
            onSearch = {  ->
                showSearchDialog = false
                showSearchResultDialog = true
            },
            viewModel = searchModel
        )
    }
    if(showSearchResultDialog){
        SearchResultsDialog(
            onDismiss = {showSearchResultDialog = false},
            onResultSelect = { recipe ->
                selectedRecipe = recipe
                showSearchResultDialog = false
                showEditorDialog = true},
            recipeList = searchModel.searchResults.collectAsState().value,
            goBack = {
                showSearchResultDialog = false
                showSearchDialog = true
            }
        )
    }
}