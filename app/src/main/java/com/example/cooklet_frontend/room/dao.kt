package com.example.cooklet_frontend.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Transaction
    @Query("SELECT * FROM aisles")
    fun getAislesWithIngredients(): Flow<List<AisleWithIngredients>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAisle(aisle: AisleEntity)

    @Query("""
        INSERT INTO ingredients (name, quantity, unit, checked, aisleName)
        VALUES (:name, :quantity, :unit, 0, :aisleName)
        ON CONFLICT(name, aisleName)
        DO UPDATE SET quantity = quantity + :quantity
    """)
    suspend fun insertOrIncrement(
        name: String,
        quantity: Double,
        unit: String,
        aisleName: String
    )

    @Update
    suspend fun updateIngredient(ingredient: IngredientEntity)

    @Query("DELETE FROM ingredients WHERE checked = 1")
    suspend fun deleteChecked()

    @Query("DELETE FROM ingredients")
    suspend fun deleteAllIngredients()
}