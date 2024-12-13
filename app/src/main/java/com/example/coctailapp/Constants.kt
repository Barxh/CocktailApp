package com.example.coctailapp

import com.example.coctailapp.ui.screens.main.content.cocktails.filter.FilterType

object Constants {
    const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    val FILTER_OPTIONS = FilterType.entries.toTypedArray()
    val FIRST_LETTER_LIST = listOf(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z"
    )
    const val LOGGED_USER = "currentLoggedInUser"
}