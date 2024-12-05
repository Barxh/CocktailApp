package com.example.coctailapp.ui.screens.main.content.cocktails.filter

enum class FilterType {
    ALCOHOLIC_OR_NOT,
    CATEGORY,
    GLASS_USED,
    INGREDIENT,
    FIRST_LETTER;


    fun toTitle(): String =
        this.toString().replace('_', ' ').lowercase().replaceFirstChar {
            it.uppercase()
        }

}