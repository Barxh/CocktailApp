package com.example.coctailapp.ui.screens.register

sealed interface RegisterEvent {

    data class RegistrationSuccessful(val email: String) : RegisterEvent
    data class RegistrationFailed(val message: String): RegisterEvent
    data object RegistrationAwait : RegisterEvent
}