package com.example.coctailapp.model

data class CocktailsPreview(
    override val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String
) : FavoritesCocktailInterface