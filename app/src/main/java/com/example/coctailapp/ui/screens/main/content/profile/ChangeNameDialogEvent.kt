package com.example.coctailapp.ui.screens.main.content.profile

sealed interface ChangeNameDialogEvent {
    data object ShowDialog : ChangeNameDialogEvent
    data object HideDialog : ChangeNameDialogEvent

}