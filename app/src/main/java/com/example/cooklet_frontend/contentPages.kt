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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.cooklet_frontend.api.RecipeViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.copy
import androidx.room3.Delete
import com.example.cooklet_frontend.models.Ingredient
import com.example.cooklet_frontend.models.Instruction
import com.example.cooklet_frontend.models.Measurement
import com.example.cooklet_frontend.models.Measures
import com.example.cooklet_frontend.models.Recipe
import com.example.cooklet_frontend.models.newRecipeIngredient
import com.example.cooklet_frontend.models.newRecipeInstruction
import com.example.cooklet_frontend.models.newRecipeMeasurement
import com.example.cooklet_frontend.models.newRecipeMeasures
import com.example.cooklet_frontend.models.newRecipePayload
import com.example.cooklet_frontend.models.newRecipeTags

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipePage(navController: NavController, viewModel: RecipeViewModel) {

    val recipes by viewModel.recipes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecipes()
    }

    LazyColumn {
        items(recipes) { recipe ->
            RecipeCard(navController, recipe)
        }
    }

//    val url = "https://picsum.photos/600/400"
//
//    Column (modifier = Modifier
//        .fillMaxWidth()
//        .fillMaxHeight(),
//        horizontalAlignment = Alignment.CenterHorizontally) {
//        Row(modifier = Modifier.fillMaxWidth()){
//            ItemSortDropdown()
//        }
//        FlowRow(modifier = Modifier
//            .fillMaxHeight()
//            .fillMaxWidth()
//            .padding(horizontal = 20.dp, vertical = 10.dp),
//            horizontalArrangement = Arrangement.spacedBy(20.dp),
//            verticalArrangement = Arrangement.spacedBy(20.dp)) {
//            RecipeCard(navController, "123")
//            RecipeCard(navController, "456")
//            RecipeCard(navController, "789")
//        }
//    }
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
fun CreatePage(viewModel: RecipeViewModel){

    var showEditorDialog by remember { mutableStateOf(false)}
    var showSearchDialog by remember {mutableStateOf(false)}


    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

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
            onClick = {
                selectedRecipe = null
                showEditorDialog = true
                      },
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
    
    if (showEditorDialog) {
        RecipeEditorDialog(
            initialRecipe = convertRecipeToPayload(selectedRecipe ?: null),
            onDismiss = { showEditorDialog = false },
            onSubmit = {
                viewModel.createRecipe(it)
                showEditorDialog = false
            }
        )
    }
    if (showSearchDialog) {
        SearchRecipeDialog(
            onDismiss = { showSearchDialog = false },
            onSearch = { recipe ->
                selectedRecipe = recipe
                showSearchDialog = false
                showEditorDialog = true
            }
        )
    }
}

@Composable
fun RecipeEditorDialog(
    initialRecipe: newRecipePayload? = null,
    onDismiss: () -> Unit,
    onSubmit: (newRecipePayload) -> Unit
) {
    // Use mutableStateListOf for dynamic lists
    val ingredients = remember { mutableStateListOf<String>() }
    val instructions = remember { mutableStateListOf<String>() }
    var recipeName by remember { mutableStateOf(initialRecipe?.title ?: "") }
    var estimatedTime by remember { mutableStateOf(initialRecipe?.readyInMinutes?.toString() ?: "") }


    // Pre-fill if editing an existing recipe
    LaunchedEffect(initialRecipe) {
        initialRecipe?.let { recipe ->
            ingredients.clear()
            ingredients.addAll(recipe.extendedIngredients.map { it.name })
            instructions.clear()
            instructions.addAll(recipe.analyzedInstructions.map { it.step })
            recipeName = initialRecipe?.title ?: ""
            estimatedTime = initialRecipe?.readyInMinutes?.toString() ?: ""
        }
        if (ingredients.isEmpty()) ingredients.add("")  // always have at least 1 box
        if (instructions.isEmpty()) instructions.add("")
    }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Recipe") },
        text = {
            // Scrollable Column for all inputs
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, max = 400.dp) // max height, will scroll
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Recipe name
                OutlinedTextField(
                    value = recipeName,
                    onValueChange = { recipeName = it },
                    label = { Text("Recipe Name*") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Estimated time
                OutlinedTextField(
                    value = estimatedTime,
                    onValueChange = { estimatedTime = it },
                    label = { Text("Estimated Time (minutes)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Ingredients dynamic list
                Text("Ingredients*")
                ingredients.forEachIndexed { index, ingredient ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = ingredient,
                            onValueChange = { ingredients[index] = it },
                            label = { Text("Ingredient ${index + 1}") },
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            ingredients.removeAt(index)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove Ingredient"
                            )
                        }
                    }
                }

                Button(onClick = { ingredients.add("") }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Ingredient")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Ingredient")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Instructions dynamic list
                Text("Instructions*")
                instructions.forEachIndexed { index, step ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = step,
                            onValueChange = { instructions[index] = it },
                            label = { Text("Step ${index + 1}") },
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { instructions.removeAt(index) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove Step"
                            )
                        }
                    }
                }

                Button(onClick = { instructions.add("") }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Step")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Step")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                // TODO: Add validation for required fields
                val baseRecipe = initialRecipe ?: newRecipePayload(
                    title = "",
                    readyInMinutes = 0,
                    extendedIngredients = emptyList(),
                    analyzedInstructions = emptyList(),
                    sourceURL = "",
                    image = "",
                    recipeTags = null,
                    servings = 0,
                    summary = "",
                    pricePerServing = 0.0,
                )

                val recipe = baseRecipe.copy(
                    title = recipeName,
                    image = "https://preview.redd.it/test-image-v0-w3kr4m2fi3111.png?width=640&crop=smart&auto=webp&s=fc50cbcaabbb33773dfe626aadedeef4c8a3f58e",
                    summary = "Baby's first recipe",
                    sourceURL = "spoonacular API",
                    readyInMinutes = estimatedTime.toIntOrNull() ?: 0,
//                    extendedIngredients = ingredients.filter { it.isNotBlank() }
//                        .mapIndexed { i, name ->
//                            newRecipeIngredient(
//                                id = i,
//                                name = name,
//                                amount = 1.0,
//                                unit = "",
//                                aisle = null,
//                                measures = newRecipeMeasures(
//                                    us = newRecipeMeasurement(1.0, ""),
//                                    metric = newRecipeMeasurement(1.0, ""),
//                                ),
//                            )
//                        },
//                    analyzedInstructions = instructions.filter { it.isNotBlank() }
//                        .mapIndexed { i, step ->
//                            newRecipeInstruction(
//                                number = i + 1,
//                                step = step,
//                                ingredients = listOf(),
//                                equipment = listOf(),
//                            )
//                        }
                    extendedIngredients = listOf(newRecipeIngredient(
                        id= 5,
                        aisle = "Walmart 24A",
                        name = "Stew Meat",
                        amount = 2.2,
                        unit = "lbs",
                        measures = newRecipeMeasures(
                            us = newRecipeMeasurement(
                                amount = 2.2,
                                unit = "lbs"
                            ),
                            metric = newRecipeMeasurement(
                                amount = 4.5,
                                unit = "kgs"
                            )
                        )

                    )
                    ),
                    analyzedInstructions = listOf(newRecipeInstruction(
                        number = 1,
                        step = "Pound meat thoroughly",
                        ingredients = listOf("Stew Meat"),
                        equipment = emptyList()
                    ))
                )
                onSubmit(recipe)
            }) {
                Text("Save Recipe")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun SearchRecipeDialog(
    onDismiss: () -> Unit,
    onSearch: (Recipe) -> Unit
) {
    var query by remember { mutableStateOf("")}

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                // input validation and save recipe TODO
                val fakeRecipe = Recipe(
                    id = null,
                    image = "",
                    title = "Test Recipe",
                    readyInMinutes = 30,
                    servings = null,
                    sourceURL = "",
                    recipeTags = null,
                    pricePerServing = null,
                    extendedIngredients = emptyList(),
                    summary = "Sample summary",
                    analyzedInstructions = emptyList(),
                    _id = null
                )


                onSearch(fakeRecipe)
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

fun convertRecipeToPayload(recipe: Recipe?): newRecipePayload? {
    val recipe = recipe ?: return null

    val tags = newRecipeTags(
        vegetarian = recipe.recipeTags?.vegetarian ?: false,
        vegan = recipe.recipeTags?.vegan ?: false,
        glutenFree = recipe.recipeTags?.glutenFree ?: false,
        dairyFree = recipe.recipeTags?.dairyFree ?: false,
        veryHealthy = recipe.recipeTags?.veryHealthy ?: false,
        cheap = recipe.recipeTags?.cheap ?: false,
    )
    val ingredients = recipe.extendedIngredients.map { ingredient -> newRecipeIngredient(
        ingredient.id,
        ingredient.aisle,
        ingredient.name,
        ingredient.amount,
        ingredient.unit,
        newRecipeMeasures(
            us = newRecipeMeasurement(
                ingredient.measures.us.amount,
                ingredient.measures.us.unit
            ),
            metric = newRecipeMeasurement(
                ingredient.measures.metric.amount,
                ingredient.measures.metric.unit
            )
        )) }

    val instructions = recipe.analyzedInstructions.map { instruction -> newRecipeInstruction(
        number = instruction.number,
        step = instruction.step,
        ingredients = instruction.ingredients,
        equipment = instruction.equipment
    ) }

    return newRecipePayload(title = recipe.title,
        image = recipe.image,
        readyInMinutes = recipe.readyInMinutes ?: 0,
        servings = recipe.servings ?: 0,
        sourceURL = recipe.sourceURL,
        recipeTags = tags,
        pricePerServing = recipe.pricePerServing ?: 0.0,
        extendedIngredients = ingredients,
        summary = recipe.summary,
        analyzedInstructions = instructions
        )
}