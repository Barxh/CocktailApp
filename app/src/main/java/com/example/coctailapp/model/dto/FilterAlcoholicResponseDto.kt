package com.example.coctailapp.model.dto

import com.example.coctailapp.model.ListConvertible

data class FilterAlcoholicResponseDto(
    val drinks: List<AlcoholicElementDto>
): ListConvertible {
    override fun toList(): List<String>{
        val list = mutableListOf<String>()
        for (string : AlcoholicElementDto in drinks)
            list.add(string.strAlcoholic)
        return list
    }
}