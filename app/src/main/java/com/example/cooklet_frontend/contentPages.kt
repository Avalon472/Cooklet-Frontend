package com.example.cooklet_frontend

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cooklet_frontend.api.RecipeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipePage(navController: NavController, viewModel: RecipeViewModel) {

    val recipes by viewModel.recipes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecipes()
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            FlowRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                recipes.forEach { recipe ->
                    RecipeCard(navController, recipe)
                }
            }
        }
    }

}

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



//    val url = "https://media.gettyimages.com/id/" +
//            "916436290/photo/fettuccine-alfredo.jpg" +
//            "?s=612x612&w=gi&k=20&c=JogNqM2LoQIWDO6USyS2KdmyFQkNK_nSu_A3SflP44U="

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

//        Text("You've reached the details page for $recipeId")
//        AsyncImage(model = url,
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxHeight(0.25f)
//                .fillMaxWidth(0.75f)
//        )
//        Text("Chicken Alfredo", fontSize = 30.sp, textDecoration = TextDecoration.Underline)
//        Row() {
//            Text("30 Minutes")
//            Icon(
//                imageVector = Icons.Filled.Timer,
//                contentDescription = "Add Button"
//            )
//        }
//        Text("Ingredients", fontSize = 25.sp, textDecoration = TextDecoration.Underline)
//        Column(modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.Start,
//            verticalArrangement = Arrangement.spacedBy(5.dp)) {
//            Text("- 1 lb spaghetti noodles")
//            Text("- 2 quarts heavy cream")
//            Text("- other alfredo ingredients")
//        }
//
//
//        Text("Instructions", fontSize = 25.sp, textDecoration = TextDecoration.Underline)
//        Column(modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.Start,
//            verticalArrangement = Arrangement.spacedBy(5.dp)) {
//            Text("- Boil the pasta until done or al dente")
//            Text("- In a separate pan, mix together cream and seasonings")
//            Text("- Finish making the pasta")
//        }

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


//
//@Composable
//fun RecipeEditorDialog(
//    initialRecipe: newRecipePayload? = null,
//    onDismiss: () -> Unit,
//    onSubmit: (newRecipePayload) -> Unit
//) {
//    // Use mutableStateListOf for dynamic lists
//    val ingredients = remember { mutableStateListOf<String>() }
//    val instructions = remember { mutableStateListOf<String>() }
//    var recipeName by remember { mutableStateOf(initialRecipe?.title ?: "") }
//    var estimatedTime by remember { mutableStateOf(initialRecipe?.readyInMinutes?.toString() ?: "") }
//
//    // Pre-fill if editing an existing recipe
//    LaunchedEffect(initialRecipe) {
//        initialRecipe?.let { recipe ->
//            ingredients.clear()
//            ingredients.addAll(recipe.extendedIngredients.map { it.name })
//            instructions.clear()
//            instructions.addAll(recipe.analyzedInstructions.map { it.step })
//            recipeName = initialRecipe?.title ?: ""
//            estimatedTime = initialRecipe?.readyInMinutes?.toString() ?: ""
//        }
//        if (ingredients.isEmpty()) ingredients.add("")  // always have at least 1 box
//        if (instructions.isEmpty()) instructions.add("")
//    }
//
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text("Create Recipe") },
//        text = {
//            // Scrollable Column for all inputs
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .heightIn(min = 0.dp, max = 400.dp) // max height, will scroll
//                    .verticalScroll(rememberScrollState()),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                // Recipe name
//                OutlinedTextField(
//                    value = recipeName,
//                    onValueChange = { recipeName = it },
//                    label = { Text("Recipe Name*") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Estimated time
//                OutlinedTextField(
//                    value = estimatedTime,
//                    onValueChange = { estimatedTime = it },
//                    label = { Text("Estimated Time (minutes)") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Ingredients dynamic list
//                Text("Ingredients*")
//                ingredients.forEachIndexed { index, ingredient ->
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        OutlinedTextField(
//                            value = ingredient,
//                            onValueChange = { ingredients[index] = it },
//                            label = { Text("Ingredient ${index + 1}") },
//                            modifier = Modifier.weight(1f)
//                        )
//                        IconButton(onClick = {
//                            ingredients.removeAt(index)
//                        }) {
//                            Icon(
//                                imageVector = Icons.Default.Delete,
//                                contentDescription = "Remove Ingredient"
//                            )
//                        }
//                    }
//                }
//
//                Button(onClick = { ingredients.add("") }) {
//                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Ingredient")
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text("Add Ingredient")
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Instructions dynamic list
//                Text("Instructions*")
//                instructions.forEachIndexed { index, step ->
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        OutlinedTextField(
//                            value = step,
//                            onValueChange = { instructions[index] = it },
//                            label = { Text("Step ${index + 1}") },
//                            modifier = Modifier.weight(1f)
//                        )
//                        IconButton(onClick = { instructions.removeAt(index) }) {
//                            Icon(
//                                imageVector = Icons.Default.Delete,
//                                contentDescription = "Remove Step"
//                            )
//                        }
//                    }
//                }
//
//                Button(onClick = { instructions.add("") }) {
//                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Step")
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text("Add Step")
//                }
//            }
//        },
//        confirmButton = {
//            Button(onClick = {
//                // TODO: Add validation for required fields
//                val baseRecipe = initialRecipe ?: defaultRecipePayload
//
//                val recipe = baseRecipe.copy(
//                    title = recipeName,
//                    image = "https://preview.redd.it/test-image-v0-w3kr4m2fi3111.png?width=640&crop=smart&auto=webp&s=fc50cbcaabbb33773dfe626aadedeef4c8a3f58e",
//                    summary = "Baby's first recipe",
//                    sourceURL = "spoonacular API",
//                    readyInMinutes = estimatedTime.toIntOrNull() ?: 0,
////                    extendedIngredients = ingredients.filter { it.isNotBlank() }
////                        .mapIndexed { i, name ->
////                            newRecipeIngredient(
////                                id = i,
////                                name = name,
////                                amount = 1.0,
////                                unit = "",
////                                aisle = null,
////                                measures = newRecipeMeasures(
////                                    us = newRecipeMeasurement(1.0, ""),
////                                    metric = newRecipeMeasurement(1.0, ""),
////                                ),
////                            )
////                        },
////                    analyzedInstructions = instructions.filter { it.isNotBlank() }
////                        .mapIndexed { i, step ->
////                            newRecipeInstruction(
////                                number = i + 1,
////                                step = step,
////                                ingredients = listOf(),
////                                equipment = listOf(),
////                            )
////                        }
//                    extendedIngredients = listOf(newRecipeIngredient(
//                        id= 5,
//                        aisle = "Walmart 24A",
//                        name = "Stew Meat",
//                        amount = 2.2,
//                        unit = "lbs",
//                        measures = newRecipeMeasures(
//                            us = newRecipeMeasurement(
//                                amount = 2.2,
//                                unit = "lbs"
//                            ),
//                            metric = newRecipeMeasurement(
//                                amount = 4.5,
//                                unit = "kgs"
//                            )
//                        )
//
//                    )
//                    ),
//                    analyzedInstructions = listOf(newRecipeInstruction(
//                        number = 1,
//                        step = "Pound meat thoroughly",
//                        ingredients = listOf("Stew Meat"),
//                        equipment = emptyList()
//                    ))
//                )
//                onSubmit(recipe)
//            }) {
//                Text("Save Recipe")
//            }
//        },
//        dismissButton = {
//            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
//        }
//    )
//}
