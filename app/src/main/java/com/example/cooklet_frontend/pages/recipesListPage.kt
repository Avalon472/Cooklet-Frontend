package com.example.cooklet_frontend.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.NoFood
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cooklet_frontend.api.RecipeViewModel
import com.example.cooklet_frontend.api.preferencesViewModel
import com.example.cooklet_frontend.api.recipeSort
import com.example.cooklet_frontend.components.RecipeCard
import com.example.cooklet_frontend.components.recipeSortDropdown

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipePage(
    navController: NavController,
    viewModel: RecipeViewModel,
    preferencesViewModel: preferencesViewModel
) {

    val recipeState by viewModel.state.collectAsState()
    val recipes by viewModel.recipes.collectAsState()
    val preferences by preferencesViewModel.appPreferences.collectAsState()
    val context = LocalContext.current
    var fetchError by remember {mutableStateOf(false)}
    var loading by remember {mutableStateOf(true)}

    LaunchedEffect(Unit) {
        viewModel.fetchRecipes()
        loading = true;
    }

    LaunchedEffect(recipeState) {
        if(recipeState == "Fetch success!") {
            viewModel.resetState()
            Toast.makeText(
                context,
                "Fetch successful. ${recipes.size} results found.",
                Toast.LENGTH_SHORT
            ).show()
            fetchError = false;
            loading = false;
        }
        else if(recipeState != "Idle" && recipeState != "Loading..."){
            viewModel.resetState()
            Toast.makeText(
                context,
                "Failed to fetch recipes. Displaying most recent saved results.",
                Toast.LENGTH_SHORT
            ).show()
            fetchError = true;
            loading = false;
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                recipeSortDropdown(preferencesViewModel)
            }

        }
        item {
            FlowRow(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (loading) {
                    Column(
                        modifier = Modifier.fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        CircularProgressIndicator(modifier = Modifier.size(60.dp))
                    }
                }
                else{
                    if (recipes.isNotEmpty()) {

                        val sortedRecipes = when (preferences.recipeSortType) {
                            recipeSort.NAME -> recipes.sortedBy { it.title }
                            recipeSort.PRICE -> recipes.sortedBy { it.pricePerServing }
                            recipeSort.TIME_TO_MAKE -> recipes.sortedBy { it.readyInMinutes }
                            recipeSort.CREATED -> recipes
                        }
                        sortedRecipes.forEach { recipe ->
                            RecipeCard(navController, recipe)
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (fetchError) {
                                Icon(
                                    Icons.Filled.ErrorOutline, contentDescription = null,
                                    Modifier.size(128.dp),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(Modifier.height(32.dp))
                                Text(
                                    "An error occurred while attempting to fetch recipes." +
                                            "Please verify you are connected to the internet and try again.",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(0.75f),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            } else {
                                Icon(
                                    Icons.Filled.NoFood, contentDescription = null,
                                    Modifier.size(128.dp),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(Modifier.height(32.dp))
                                Text(
                                    "No recipe has been created yet. " +
                                            "Head over to the create tab to make your first recipe!",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(0.75f),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}