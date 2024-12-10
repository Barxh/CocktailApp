package com.example.coctailapp.model.dto

data class CocktailsDetailsResponseDto(
    val drinks: List<CocktailDetailsDto>?
){
    fun toCocktailsResponse() : CocktailResponseDto {
        val list = mutableListOf<CocktailsPreviewDto>()
        if (drinks != null) {
            for (cocktailsDetails: CocktailDetailsDto in drinks) {
                list.add(
                    CocktailsPreviewDto(
                        cocktailsDetails.idDrink,
                        cocktailsDetails.strDrink,
                        cocktailsDetails.strDrinkThumb
                    )
                )
            }
            return  CocktailResponseDto(list)
        }
        return CocktailResponseDto(null)

    }
}