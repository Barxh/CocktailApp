package com.example.coctailapp.ui.screens.main.content.profile

sealed interface ChangeNameStatus {
    data object RequestPending : ChangeNameStatus
    data class InputErrorEvent(val errorMessage : String) : ChangeNameStatus
}