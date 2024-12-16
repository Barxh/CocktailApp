package com.example.coctailapp.ui.screens.main.content.profile

sealed interface LogoutDialogEvent {

    data object ShowDialog : LogoutDialogEvent
    data object HideDialog : LogoutDialogEvent
    data object LogoutEvent : LogoutDialogEvent
}