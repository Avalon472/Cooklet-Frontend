package com.example.cooklet_frontend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cooklet_frontend.api.RecipeViewModel
import com.example.cooklet_frontend.api.SearchResultViewModel
import com.example.cooklet_frontend.api.ingredientViewModel
import com.example.cooklet_frontend.api.measurementUnit
import com.example.cooklet_frontend.api.preferencesViewModel
import com.example.cooklet_frontend.pages.CreatePage
import com.example.cooklet_frontend.pages.IngredientsPage
import com.example.cooklet_frontend.pages.RecipeDetailsPage
import com.example.cooklet_frontend.pages.RecipePage

@Composable
fun AppContent(
    viewModel: RecipeViewModel,
    ingredientViewModel: ingredientViewModel,
    searchModel: SearchResultViewModel,
    preferencesViewModel: preferencesViewModel
){
    val navController = rememberNavController()

    Scaffold(
        topBar = { AppHeader(navController, preferencesViewModel) },
        bottomBar = { AppFooter(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            AppBody(navController, viewModel, ingredientViewModel, searchModel, preferencesViewModel)
        }
    }
}

@Composable
fun AppHeader(navController: NavController, preferencesViewModel: preferencesViewModel){
    // Observe the current back stack entry as a state
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val preferences by preferencesViewModel.appPreferences.collectAsState()

    // Get the route from the current destination
    val currentRoute = navBackStackEntry?.destination?.route
    val routeText = if(currentRoute == "RecipeDetails/{recipeId}"){
        "Recipe Details"
    }
    else{
        currentRoute
    }

    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .statusBarsPadding()
            .padding(bottom = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp)
        ){
            Image(
                painterResource(id = R.drawable.cooklet_logo),
                contentDescription = "Cooklet Logo",
                modifier = Modifier .size(100.dp),
                )
        }

        Text(
            text = routeText ?: "Cooklet",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 35.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 12.dp)
        ) {
            IconButton(
                onClick = { menuExpanded = true },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Dark Mode")
                            Switch(
                                checked = !preferences.lightMode,
                                onCheckedChange = { preferencesViewModel.changeTheme() }
                            )
                        }
                    },
                    onClick = {}
                )
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Metric Units")
                            Spacer(modifier = Modifier.width(4.dp))
                            Switch(
                                checked = preferences.unitType == measurementUnit.METRIC,
                                onCheckedChange = { preferencesViewModel.changeUnitPreference() }
                            )
                        }
                    },
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun AppFooter(navController: NavController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        modifier = Modifier.border(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {

        NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,

                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),

                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
            selected = currentRoute == "Recipes",
            onClick = { navController.navigate("Recipes") },
            icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = "Recipes") },
            label = { Text("Recipes") }
        )

        NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,

                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),

                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
            selected = currentRoute == "Ingredients",
            onClick = { navController.navigate("Ingredients") },
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Ingredients") },
            label = { Text("Ingredients") }
        )

        NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,

                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),

                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
            selected = currentRoute == "Create",
            onClick = { navController.navigate("Create") },
            icon = {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create")//,
                    //modifier = Modifier.size(28.dp)
                   },
            label = {
                Text("Create")
            }
        )


    }

//    Row(modifier = Modifier.fillMaxHeight(0.085f).fillMaxWidth()
//        .background(MaterialTheme.colorScheme.primaryContainer).padding(bottom = 10.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly) {
//        Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
//            onClick = {navController.navigate(route = "Recipes")}) {
//            Text("Recipes")
//        }
//        Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
//            onClick = {navController.navigate(route = "Ingredients")}) {
//            Text("Ingredients")
//        }
//        Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
//            onClick = {navController.navigate(route = "Create")}) {
//            Text("Create")
//        }
//
//    }
}

@Composable
fun AppBody(
    navController: NavHostController,
    viewModel: RecipeViewModel,
    ingredientViewModel: ingredientViewModel,
    searchModel: SearchResultViewModel,
    preferencesViewModel: preferencesViewModel){

    NavHost(navController, startDestination = "Recipes") {

        composable("Recipes") {
        RecipePage(navController, viewModel, preferencesViewModel) }

        composable(
            route = "RecipeDetails/{recipeId}",
            arguments = listOf(
                navArgument("recipeId") { type = NavType.StringType }
            )
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId")
                RecipeDetailsPage(recipeId?: "", viewModel, navController, preferencesViewModel)
            }

        composable("Ingredients") {

            IngredientsPage(viewModel, ingredientViewModel, preferencesViewModel)
        }
        composable("Create") {

            CreatePage(viewModel, searchModel)
        }
    }
}