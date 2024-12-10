package com.example.coctailapp.model.dto

import com.example.coctailapp.model.ListConvertible

data class FilterGlassResponseDto(
    val drinks: List<GlassElementDto>
): ListConvertible {
    override fun toList(): List<String>{
        val list = mutableListOf<String>()
        for (string : GlassElementDto in drinks)
            list.add(string.strGlass)
        return list
    }
}