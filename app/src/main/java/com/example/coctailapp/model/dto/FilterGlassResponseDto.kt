package com.example.coctailapp.model.dto

import com.example.coctailapp.model.ListConvertible

data class FilterGlassResponseDto(
    val drinks: List<GlassElementDto>
): ListConvertible {
    override fun toList(): List<String> = drinks.map { filter ->
        filter.strGlass
    }
}