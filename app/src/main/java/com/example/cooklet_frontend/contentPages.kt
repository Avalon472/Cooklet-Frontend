package com.example.cooklet_frontend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipePage(navController: NavController) {
    val url = "https://picsum.photos/600/400"
    val painter = rememberAsyncImagePainter(url)

    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth()){
            ItemSortDropdown()
        }
        FlowRow(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)) {
            RecipeCard(navController, "123")
            RecipeCard(navController, "456")
            RecipeCard(navController, "789")
        }
    }
}

@Composable
fun RecipeDetailsPage(recipeId: String?){
    val url = "https://media.gettyimages.com/id/" +
            "916436290/photo/fettuccine-alfredo.jpg" +
            "?s=612x612&w=gi&k=20&c=JogNqM2LoQIWDO6USyS2KdmyFQkNK_nSu_A3SflP44U="
    Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text("You've reached the details page for $recipeId")
        AsyncImage(model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .fillMaxWidth(0.75f)
        )
        Text("Chicken Alfredo", fontSize = 30.sp, textDecoration = TextDecoration.Underline)
        Row() {
            Text("30 Minutes")
            Icon(
                imageVector = Icons.Filled.Timer,
                contentDescription = "Add Button"
            )
        }
        Text("Ingredients", fontSize = 25.sp, textDecoration = TextDecoration.Underline)
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("- 1 lb spaghetti noodles")
            Text("- 2 quarts heavy cream")
            Text("- other alfredo ingredients")
        }


        Text("Instructions", fontSize = 25.sp, textDecoration = TextDecoration.Underline)
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("- Boil the pasta until done or al dente")
            Text("- In a separate pan, mix together cream and seasonings")
            Text("- Finish making the pasta")
        }

    }
}

@Composable
fun IngredientsPage(){
    Column(modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth()){
            ItemSortDropdown()
        }

        IngredientItem(item = "Ground beef", quantity = 1, unit = "lbs")
        IngredientItem("Spaghetti", 2, "lbs")
        IngredientItem("Heavy cream", 3, "quarts")
    }
}

@Composable
fun CreatePage(){
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
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