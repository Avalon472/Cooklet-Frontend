package com.example.cooklet_frontend.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cooklet_frontend.api.SearchResultViewModel
import com.example.cooklet_frontend.models.Recipe

@Composable
fun SearchResultsDialog(
    onDismiss: () -> Unit,
    recipeList: List<Recipe>,
    onResultSelect: (Recipe) -> Unit,
    goBack: () -> Unit = {}
){

    var selectedRecipe: Recipe? by remember {
        mutableStateOf(null)
    }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Recipe Results") },
        text = {
            Column() {
                if(recipeList.isEmpty()){
                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text("No Recipes were Found.",
                            fontSize = 60.sp,
                            lineHeight = 50.sp,
                            textAlign = TextAlign.Center)
                    }
                }
                else {
                    Text("Select a Recipe to Continue.")
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight(0.75f)
                            .fillMaxWidth()
                    ) {
                        items(
                            recipeList
                        ) { result ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable { selectedRecipe = result },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (result == selectedRecipe)
                                        MaterialTheme.colorScheme.surfaceVariant
                                    else
                                        MaterialTheme.colorScheme.surface
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
                                    Text(
                                        text = result.title,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "Price Per Serving: $${"%.2f".format(result.pricePerServing ?: 0.0)}",
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                    Text(
                                        text = "⏱ ${result.readyInMinutes ?: 0} min",
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }

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
        },

        dismissButton = {
            OutlinedButton(onClick = goBack) {
                Text("Go Back")
            }
        },
    )


}