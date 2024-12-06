package com.example.coctailapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object LoginScreen

    @Serializable
    object RegisterScreen

    @Serializable
    data class MainScreen(val email: String)

    @Serializable
    object CocktailsContent

    @Serializable
    object ShoppingList

    @Serializable
    object Profile

    @Serializable
    object CocktailsFragment

    @Serializable
    object  FilterFragment

    @Serializable
    object  SearchFragment

    @Serializable
    object  FilterDetailsFragment
}
