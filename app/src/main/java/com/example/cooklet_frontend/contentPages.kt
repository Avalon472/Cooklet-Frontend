package com.example.cooklet_frontend

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipePage() {
    val url = "https://picsum.photos/600/400"
    val painter = rememberAsyncImagePainter(url)

    Column (modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("You've reached the recipe page")
        FlowRow(modifier = Modifier.fillMaxHeight().fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)) {
            RecipeCard()
            RecipeCard()
            RecipeCard()
        }
//        Text(painter.state.toString())
    }
}

@Composable
fun IngredientsPage(){
    Column(modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Top) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            var isChecked by remember{ mutableStateOf(false) }

            Checkbox(checked = isChecked, onCheckedChange = {checkStatus -> isChecked = checkStatus})
            Text("Meat")
        }
    }
}

@Composable
fun CreatePage(){
    Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Option 1")
        Text("Option 2")
        Text("Option 3")
    }
}

@Composable
fun ProfilePage(){
    Text("Profile page goes here")
}