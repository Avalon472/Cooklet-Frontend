package com.example.cooklet_frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun ContentContainer(){
    val navController = rememberNavController()

    Scaffold(topBar = {
//        HeaderContent(navController)
        Header1()
    },
        bottomBar = {
            FooterNav(navController)
        }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            BodyContent(navController)
        }
    }
}

@Composable
fun FooterNav(navController: NavController) {
    Row (modifier = Modifier.background(Color.Red).fillMaxWidth()){
        Button(onClick = { navController.navigate("home") }) {
            Text("Home")
        }

        Button(onClick = { navController.navigate("profile") }) {
            Text("Profile")
        }
    }
}

//@Composable
//fun HeaderContent(navController: NavHostController){
//    NavHost(navController, startDestination = "home") {
//
//        composable("home") { Header1() }
//        composable("profile") { Header2() }
//    }
//}

@Composable
fun Header1(){
    Row(modifier = Modifier.fillMaxWidth().background(Color.Cyan).height(60.dp)) {
        Text("Header content goes here")
        Text("Here is more header content")
    }
}

@Composable
fun Header2(){
    Column (modifier = Modifier.fillMaxWidth().background(Color.Cyan).height(60.dp)) {
        Text("Header content goes here")
        Text("Here is more header content")
    }
}
@Composable
fun BodyContent(navController: NavHostController) {
//    NavHost(navController, startDestination = "home") {
//
//        composable("home") { BodyHome() }
//        composable("profile") { BodyProfile() }
//    }
}

@Composable
fun BodyHome(){
    Column() {
        Row() {
            Checkbox(checked = false, onCheckedChange = {})
            Text("This is the home page for the content body")
        }
        Row() {
            Checkbox(checked = true, onCheckedChange = {})
            Text("This is the home page for the content body again")
        }
    }

}

@Composable
fun BodyProfile(){
    Text("This is the profile page for the content body")
}