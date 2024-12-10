package com.example.coctailapp.model

import androidx.room.Entity


@Entity(tableName = "SHOPPING_LIST_INGREDIENT", primaryKeys = ["userId", "cocktailId", "cocktailIngredientName"])
data class ShoppingListIngredient(
    val userId: String,
    val cocktailId: String,
    val cocktailName: String,
    val cocktailIngredientName: String,
    val cocktailIngredientMeasure: String?
)
