package com.example.coctailapp.ui.screens.main.content.cocktails

import com.example.coctailapp.model.dto.CocktailsPreviewDto


sealed interface CocktailsFetchingEvent {

    data object LoadingEvent : CocktailsFetchingEvent
    data class SuccessEvent(val cocktailsList : List<CocktailsPreviewDto>): CocktailsFetchingEvent
    data class ErrorEvent(val errorMessage: String): CocktailsFetchingEvent
}