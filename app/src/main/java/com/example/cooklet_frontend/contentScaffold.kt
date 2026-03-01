package com.example.cooklet_frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

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

    Row(modifier = Modifier.fillMaxHeight(0.1f).fillMaxWidth()
        .background(Color.Blue),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Text(text = currentRoute ?: "Cooklet",
            color = Color.White,
            fontSize = 35.sp)
    }
}

@Composable
fun AppFooter(navController: NavController){
    Row(modifier = Modifier.fillMaxHeight(0.085f).fillMaxWidth()
        .background(Color.Blue).padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Button(onClick = {navController.navigate(route = "Recipes")}) {
            Text("Recipes")
        }
        Button(onClick = {navController.navigate(route = "Ingredients")}) {
            Text("Ingredients")
        }
        Button(onClick = {navController.navigate(route = "Create")}) {
            Text("Create")
        }
        Button(onClick = {navController.navigate(route = "Profile")}) {
            Text("Profile")
        }
    }
}

@Composable
fun AppBody(navController: NavHostController){
        NavHost(navController, startDestination = "Recipes") {

        composable("Recipes") { RecipePage() }
        composable("Ingredients") { IngredientsPage() }
        composable("Create") { CreatePage() }
        composable("Profile") { ProfilePage() }
    }
}