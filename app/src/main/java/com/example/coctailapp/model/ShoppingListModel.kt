package com.example.coctailapp.model

data class ShoppingListModel(
    val recipeId : String,
    val recipeName: String,
    val neededIngredient: MutableList<Ingredient>
)
