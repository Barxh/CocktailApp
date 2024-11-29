package com.example.coctailapp.model

sealed interface RegisterEvent {

    data object RegistrationSuccessful : RegisterEvent
    data class RegistrationFailed(val message: String): RegisterEvent
    data object RegistrationAwait : RegisterEvent
}