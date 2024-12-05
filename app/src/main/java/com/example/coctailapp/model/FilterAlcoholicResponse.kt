package com.example.coctailapp.model

data class FilterAlcoholicResponse(
    val drinks: List<AlcoholicElement>
):ListConvertible{
    override fun toList(): List<String>{
        val list = mutableListOf<String>()
        for (string : AlcoholicElement in drinks)
            list.add(string.strAlcoholic)
        return list
    }
}