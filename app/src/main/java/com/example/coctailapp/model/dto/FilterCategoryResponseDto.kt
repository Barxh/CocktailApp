package com.example.coctailapp.model.dto

import com.example.coctailapp.model.ListConvertible

data class FilterCategoryResponseDto(
    val drinks: List<CategoryElementDto>
): ListConvertible {
    override fun toList(): List<String> =drinks.map { filter ->
        filter.strCategory
    }
}