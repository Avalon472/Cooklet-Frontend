package com.example.cooklet_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cooklet_frontend.api.IngredientViewModelFactory
import com.example.cooklet_frontend.api.PreferencesViewModelFactory
import com.example.cooklet_frontend.api.RecipeViewModel
import com.example.cooklet_frontend.api.RecipeViewModelFactory
import com.example.cooklet_frontend.api.SearchResultViewModel
import com.example.cooklet_frontend.api.ingredientViewModel
import com.example.cooklet_frontend.api.preferencesViewModel
import com.example.cooklet_frontend.ui.theme.CookletFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current

            val viewModel: RecipeViewModel = viewModel(
                factory = RecipeViewModelFactory(
                context.applicationContext
            )
            )

            val ingredientViewModel: ingredientViewModel = viewModel(
                factory = IngredientViewModelFactory(
                context.applicationContext
            )
            )

            val searchModel: SearchResultViewModel = viewModel()

            val preferencesViewModel: preferencesViewModel =  viewModel(
                factory = PreferencesViewModelFactory(
                context.applicationContext
            )
            )

            CookletFrontendTheme (
                darkTheme = !preferencesViewModel.appPreferences.collectAsState().value.lightMode
            ){
                AppContent(viewModel, ingredientViewModel, searchModel, preferencesViewModel)
            }
        }
    }
}
