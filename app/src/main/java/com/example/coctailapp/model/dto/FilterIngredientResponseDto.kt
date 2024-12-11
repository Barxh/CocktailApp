package com.example.coctailapp.model.dto

import com.example.coctailapp.model.ListConvertible

data class FilterIngredientResponseDto(
    val drinks: List<IngredientElementDto>
): ListConvertible {
    override fun toList(): List<String> = drinks.map { filter ->
        filter.strIngredient1
    }
}