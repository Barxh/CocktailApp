package com.example.coctailapp.ui.screens.register

sealed interface RegisterEvent {

    data object RegistrationSuccessful : RegisterEvent
    data class RegistrationFailed(val message: String): RegisterEvent
    data object RegistrationAwait : RegisterEvent
}