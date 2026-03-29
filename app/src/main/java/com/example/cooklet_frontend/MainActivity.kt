package com.example.cooklet_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cooklet_frontend.api.RecipeViewModel
import com.example.cooklet_frontend.ui.theme.CookletFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            CookletFrontendTheme {
//                AppContent()
//            }
            RecipeScreen()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CookletFrontendTheme {
        Greeting("Android")
    }
}

@Composable
fun RecipeScreen(viewModel: RecipeViewModel = viewModel()) {

    val state = viewModel.state.collectAsState()
    val recipes = viewModel.recipes.collectAsState()

    Column {

        Button(onClick = {
            viewModel.searchRecipes("chicken soup")
        }) {
            Text("Search Recipes")
        }

        Button(onClick = {
            viewModel.fetchRecipes()
        }) {
            Text("Fetch Recipes")
        }

        Text(state.value)

        recipes.value.forEach {
            Text(it.title)
            it.analyzedInstructions.forEach { ins ->
            Text(ins.step)
            Text("")}
        }
    }
}
