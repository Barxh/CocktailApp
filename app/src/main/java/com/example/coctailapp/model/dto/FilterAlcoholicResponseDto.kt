package com.example.coctailapp.model.dto

import com.example.coctailapp.model.ListConvertible

data class FilterAlcoholicResponseDto(
    val drinks: List<AlcoholicElementDto>
): ListConvertible {
    override fun toList(): List<String> = drinks.map { filter ->
        filter.strAlcoholic
    }

}