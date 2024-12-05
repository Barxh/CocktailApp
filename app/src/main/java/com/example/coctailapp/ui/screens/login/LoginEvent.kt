package com.example.coctailapp.ui.screens.login

sealed interface LoginEvent {

    data class LoginSuccess(val email: String) : LoginEvent
    data class LoginFailed(val message: String): LoginEvent
    data object LoginWait : LoginEvent

}