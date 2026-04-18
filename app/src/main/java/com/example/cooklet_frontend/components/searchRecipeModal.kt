package com.example.cooklet_frontend.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cooklet_frontend.api.SearchResultViewModel

@Composable
fun SearchRecipeDialog(
    onDismiss: () -> Unit,
    onSearch: () -> Unit,
    viewModel: SearchResultViewModel
) {
    var query by remember { mutableStateOf("")}
    val recipeState by viewModel.searchState.collectAsState()
    val context = LocalContext.current
    var fetchError by remember {mutableStateOf(false)}
    var loading by remember {mutableStateOf(false)}
    var madeQuery by remember {mutableStateOf(false)}

    LaunchedEffect(recipeState) {
        if(recipeState == "Query successful!") {
            viewModel.resetState()
            Toast.makeText(
                context,
                "Fetch successful. Displaying results.",
                Toast.LENGTH_SHORT
            ).show()
            fetchError = false;
            loading = false;
        }
        else if(recipeState != "Idle" && recipeState != "Loading..."){
            viewModel.resetState()
            Toast.makeText(
                context,
                "Failed to search for recipes. " +
                        "Please confirm your internet connection and try again.",
                Toast.LENGTH_SHORT
            ).show()
            fetchError = true;
            loading = false;
        }
    }

    if(!loading && !fetchError && madeQuery) onSearch()

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                viewModel.searchRecipes(query)
                loading = true
                madeQuery = true
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
                if(loading){
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(60.dp))
                    }
                }
                else {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        label = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Recipe"
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}