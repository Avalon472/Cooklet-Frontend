package com.example.cooklet_frontend.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cooklet_frontend.api.SearchResultViewModel
import com.example.cooklet_frontend.models.Recipe

@Composable
fun SearchResultsDialog(
    onDismiss: () -> Unit,
    viewModel: SearchResultViewModel,
    onResultSelect: (Recipe) -> Unit
){

    val results = viewModel.searchResults.collectAsState()
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
                    results.value
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

//                    result ->
//                    Column(modifier = Modifier.clickable{selectedRecipe = result
//                    }.background(
//                        if (result == selectedRecipe) Color.LightGray else Color.Transparent
//                    ).padding(8.dp)){
//                        Text(result.title, fontSize = 26.sp)
//                        Text(text = "Price: $${"%.2f".format(result.pricePerServing ?: 0.0)}")
//                        Text(text = "Ready in: ${result.readyInMinutes} minutes")
//                    }
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