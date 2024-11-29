package com.example.coctailapp.model

sealed interface LoginEvent {

    data object LoginSuccess : LoginEvent
    data class LoginFailed(val message: String):LoginEvent
    data object LoginWait : LoginEvent

}