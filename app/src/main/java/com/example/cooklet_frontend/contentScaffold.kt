package com.example.cooklet_frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun AppContent(){
    val navController = rememberNavController()
    Scaffold(
        topBar = { AppHeader(navController) },
        bottomBar = { AppFooter(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            AppBody(navController)
        }
    }
}

@Composable
fun AppHeader(navController: NavController){
    // Observe the current back stack entry as a state
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Get the route from the current destination
    val currentRoute = navBackStackEntry?.destination?.route
    val routeText = if(currentRoute == "RecipeDetails/{recipeId}"){
        "Recipe Details"
    }
    else{
        currentRoute
    }

    Row(modifier = Modifier.fillMaxHeight(0.1f).fillMaxWidth()
        .background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Text(text = routeText ?: "Cooklet",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 35.sp)
    }
}

@Composable
fun AppFooter(navController: NavController){
    Row(modifier = Modifier.fillMaxHeight(0.085f).fillMaxWidth()
        .background(MaterialTheme.colorScheme.primaryContainer).padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
            onClick = {navController.navigate(route = "Recipes")}) {
            Text("Recipes")
        }
        Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
            onClick = {navController.navigate(route = "Ingredients")}) {
            Text("Ingredients")
        }
        Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
            onClick = {navController.navigate(route = "Create")}) {
            Text("Create")
        }
        Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
            onClick = {navController.navigate(route = "Profile")}) {
            Text("Profile")
        }
    }
}

@Composable
fun AppBody(navController: NavHostController){
        NavHost(navController, startDestination = "Recipes") {

        composable("Recipes") { RecipePage(navController) }

        composable(
            route = "RecipeDetails/{recipeId}",
            arguments = listOf(
                navArgument("recipeId") { type = NavType.StringType }
            )
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId")
                RecipeDetailsPage(recipeId)
            }

        composable("Ingredients") { IngredientsPage() }
        composable("Create") { CreatePage() }
        composable("Profile") { ProfilePage() }
    }
}