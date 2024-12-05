package com.example.coctailapp.model

data class FilterCategoryResponse(
    val drinks: List<CategoryElement>
):ListConvertible{
    override fun toList(): List<String>{
        val list = mutableListOf<String>()
        for (string : CategoryElement in
        drinks)
            list.add(string.strCategory)
        return list
    }
}