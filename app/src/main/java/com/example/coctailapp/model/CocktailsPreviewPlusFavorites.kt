package com.example.coctailapp.model

data class CocktailsPreviewPlusFavorites(
    val idDrink : String,
    val cocktailName : String,
    val imageURL : String,
    val favorite : Boolean
)