package com.example.coctailapp.ui.screens.main.content.cocktails.search

sealed interface SearchRegulation {
    data object SearchApproved : SearchRegulation
    data class SearchDenied(val message : String) : SearchRegulation
    data object SearchPending : SearchRegulation
}