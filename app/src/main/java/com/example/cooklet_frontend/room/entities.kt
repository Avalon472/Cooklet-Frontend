package com.example.cooklet_frontend.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "aisles")
data class AisleEntity(
    @PrimaryKey val name: String
)

@Entity(
    tableName = "ingredients",
    foreignKeys = [
        ForeignKey(
            entity = AisleEntity::class,
            parentColumns = ["name"],
            childColumns = ["aisleName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["aisleName"]),
        Index(value = ["name", "aisleName"], unique = true) // 👈 THIS is the key
    ]
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val quantity: Double,
    val unit: String,
    val checked: Boolean,
    val aisleName: String
)

data class AisleWithIngredients(
    @Embedded val aisle: AisleEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "aisleName"
    )
    val ingredients: List<IngredientEntity>
)