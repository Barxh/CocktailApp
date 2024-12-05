package com.example.coctailapp.model

data class FilterIngredientResponse(
    val drinks: List<IngredientElement>
):ListConvertible{
    override fun toList(): List<String>{
        val list = mutableListOf<String>()
        for (string : IngredientElement in drinks)
            list.add(string.strIngredient1)
        return list
    }
}