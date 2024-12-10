package com.example.coctailapp.model.dto

import com.example.coctailapp.model.ListConvertible

data class FilterIngredientResponseDto(
    val drinks: List<IngredientElementDto>
): ListConvertible {
    override fun toList(): List<String>{
        val list = mutableListOf<String>()
        for (string : IngredientElementDto in drinks)
            list.add(string.strIngredient1)
        return list
    }
}