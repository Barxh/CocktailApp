package com.example.coctailapp.ui.screens.main.content.shopping

sealed interface ShoppingListDialogEvent {
    data object DialogDismissedEvent : ShoppingListDialogEvent
    data object PendingDialogEvent : ShoppingListDialogEvent
}