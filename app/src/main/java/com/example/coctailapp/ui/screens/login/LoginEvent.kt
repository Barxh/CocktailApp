package com.example.coctailapp.ui.screens.login

sealed interface LoginEvent {

    data object LoginSuccess : LoginEvent
    data class LoginFailed(val message: String): LoginEvent
    data object LoginWait : LoginEvent

}