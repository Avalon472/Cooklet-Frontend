package com.example.cooklet_frontend

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipePage(navController: NavController) {
    val url = "https://picsum.photos/600/400"

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
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            ItemSortDropdown()



            Button(onClick = { /* Add new recipe ingredients TODO */ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Recipe"
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Add Recipe Ingredients", textAlign = TextAlign.Center)
            }
        }

        IngredientItem(item = "Ground beef", quantity = 1, unit = "lbs")
        IngredientItem("Spaghetti", 2, "lbs")
        IngredientItem("Heavy cream", 3, "quarts")
    }
}

@Composable
fun CreatePage(){

    var showManualDialog by remember { mutableStateOf(false)}
    var showSearchDialog by remember {mutableStateOf(false)}

    var recipeName by remember { mutableStateOf("")}
    var ingredients by remember { mutableStateOf("")}
    var estimatedTime by remember { mutableStateOf("")}
    var instructions by remember { mutableStateOf("")}


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Recipe",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = { showManualDialog = true },
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Manually")
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = { showSearchDialog = true },
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Recipe")
        }
    }
    
    if (showManualDialog) {
        ManualRecipeDialog(
            onDismiss = { showManualDialog = false },
            recipeName,
            ingredients,
            estimatedTime,
            instructions
        )
    }
    if (showSearchDialog) {
        SearchRecipeDialog(
            onDismiss = { showSearchDialog = false },
            onSearch = {recipe:Array<String> ->
                recipeName = recipe[0]
                ingredients = recipe[1]
                estimatedTime = recipe[2]
                instructions = recipe[3]
                showManualDialog = true
            }
        )
    }
}

@Composable
fun ManualRecipeDialog(onDismiss: () -> Unit, recipeName: String = "", ingredients: String = "",
                       estimatedTime: String = "", instructions: String = "") {
    
    var recipeName by remember { mutableStateOf(recipeName)}
    var ingredients by remember { mutableStateOf(ingredients)}
    var estimatedTime by remember { mutableStateOf(estimatedTime)}
    var instructions by remember { mutableStateOf(instructions)}
    
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                // input validation and save recipe TODO
                onDismiss()
            }) {
                Text("Save Recipe")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Create Recipe") },
        text = {
            Column {

                OutlinedTextField(
                    value = recipeName,
                    onValueChange = { recipeName = it },
                    label = { Text("Recipe Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ingredients,
                    onValueChange = { ingredients = it },
                    label = { Text("Ingredients") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))


                OutlinedTextField(
                    value = estimatedTime,
                    onValueChange = { estimatedTime = it },
                    label = { Text("Estimated Time") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))


                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Instructions") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Composable
fun SearchRecipeDialog(onDismiss: () -> Unit, onSearch: (Array<String>) -> Unit){
    var query by remember { mutableStateOf("")}
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                // input validation and save recipe TODO
                onSearch(arrayOf("Lasagna", "Pasta, Tomato Sauce", "45min", "Bake in oven"))
                onDismiss()

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

                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Recipe"
                    ) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}