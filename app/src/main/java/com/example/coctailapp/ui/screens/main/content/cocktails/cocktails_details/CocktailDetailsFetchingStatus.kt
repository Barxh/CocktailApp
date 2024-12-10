package com.example.coctailapp.ui.screens.main.content.cocktails.cocktails_details

import com.example.coctailapp.model.CocktailDetails

sealed interface CocktailDetailsFetchingStatus {

    data object LoadingEvent : CocktailDetailsFetchingStatus
    data class ErrorEvent(val errorMessage: String) : CocktailDetailsFetchingStatus
    data class SuccessEvent(val cocktailDetails: CocktailDetails) : CocktailDetailsFetchingStatus


}