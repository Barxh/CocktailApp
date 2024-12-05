package com.example.coctailapp.ui.screens.main.content.cocktails.filter

sealed interface FilterFetchingEvent {
    data object LoadingEvent : FilterFetchingEvent
    data class SuccessEvent(val filtersList : List<String>):  FilterFetchingEvent
    data class ErrorEvent(val errorMessage: String):  FilterFetchingEvent
}