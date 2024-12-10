package com.example.coctailapp.model

data class CocktailsDetailsResponseTwo(
    val drinks: List<CocktailDetails>?
){
    fun toCocktailsResponse() : CocktailResponse {
        val list = mutableListOf<CocktailsPreview>()
        if (drinks != null) {
            for (cocktailsDetails: CocktailDetails in drinks) {
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