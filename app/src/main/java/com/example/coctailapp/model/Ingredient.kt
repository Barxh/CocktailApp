package com.example.coctailapp.model

data class Ingredient(
    val ingredientsName: String,
    val ingredientMeasure : String?,
    val isInShoppingList : Boolean = false
)
