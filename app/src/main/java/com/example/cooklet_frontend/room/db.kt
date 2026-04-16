package com.example.cooklet_frontend.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.cooklet_frontend.api.ingredientViewModel

@Database(
    entities = [AisleEntity::class, IngredientEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
}

class IngredientViewModelFactory(
    private val dao: IngredientDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ingredientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ingredientViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}