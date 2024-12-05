package com.example.coctailapp.model

data class FilterGlassResponse(
    val drinks: List<GlassElement>
):ListConvertible{
    override fun toList(): List<String>{
        val list = mutableListOf<String>()
        for (string : GlassElement in drinks)
            list.add(string.strGlass)
        return list
    }
}