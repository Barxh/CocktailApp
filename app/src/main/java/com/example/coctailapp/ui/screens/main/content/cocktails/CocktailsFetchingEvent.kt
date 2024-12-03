package com.example.coctailapp.ui.screens.main.content.cocktails

import com.example.coctailapp.model.CocktailsPreview


sealed interface CocktailsFetchingEvent {

    data object LoadingEvent : CocktailsFetchingEvent
    data class SuccessEvent(val cocktailsList : List<CocktailsPreview>): CocktailsFetchingEvent
    data class ErrorEvent(val errorMessage: String): CocktailsFetchingEvent
}