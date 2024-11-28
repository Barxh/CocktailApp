package com.example.coctailapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object LoginScreen

    @Serializable
    object RegisterScreen

}