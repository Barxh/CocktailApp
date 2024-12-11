package com.example.coctailapp.model.dto

import com.example.coctailapp.model.FavoritesCocktailInterface
import kotlinx.serialization.SerialName

data class CocktailsPreviewDto(
    override val idDrink: String,
    @SerialName("strDrink")
    val strDrink: String,
    val strDrinkThumb: String
) : FavoritesCocktailInterface