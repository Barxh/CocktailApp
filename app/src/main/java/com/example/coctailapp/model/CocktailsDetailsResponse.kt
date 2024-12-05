package com.example.coctailapp.model

data class CocktailsDetailsResponse(
    val drinks: List<CocktailsDetails>?
) {
    fun toCocktailsResponse() : CocktailResponse {
        val list = mutableListOf<CocktailsPreview>()
        if (drinks != null) {
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
        return CocktailResponse(null)

    }
}