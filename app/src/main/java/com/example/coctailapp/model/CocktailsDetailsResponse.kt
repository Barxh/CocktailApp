package com.example.coctailapp.model

data class CocktailsDetailsResponse(
    val drinks: List<CocktailsDetails>
) {
    fun toCocktailsResponse() : CocktailResponse {
        val list = mutableListOf<CocktailsPreview>()
        for (cocktailsDetails: CocktailsDetails in drinks) {
            list.add(
                CocktailsPreview(
                    cocktailsDetails.idDrink,
                    cocktailsDetails.strDrink,
                    cocktailsDetails.strDrinkThumb
                )
            )
        }
        return  CocktailResponse(list)
    }
}