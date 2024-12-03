package com.example.coctailapp.ui.screens.main.content.cocktails


sealed interface CocktailsFetchingEvent {

    data object LoadingEvent : CocktailsFetchingEvent
    data object SuccessEvent: CocktailsFetchingEvent
    data class ErrorEvent(val errorMessage: String): CocktailsFetchingEvent
}