package com.example.coctailapp.model

import kotlinx.serialization.SerialName

data class CocktailsPreview(
    override val idDrink: String,
    @SerialName("strDrink")
    val strDrink: String,
    val strDrinkThumb: String
) : FavoritesCocktailInterface