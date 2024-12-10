package com.example.coctailapp.model.dto

import com.example.coctailapp.model.ListConvertible

data class FilterCategoryResponseDto(
    val drinks: List<CategoryElementDto>
): ListConvertible {
    override fun toList(): List<String>{
        val list = mutableListOf<String>()
        for (string : CategoryElementDto in
        drinks)
            list.add(string.strCategory)
        return list
    }
}