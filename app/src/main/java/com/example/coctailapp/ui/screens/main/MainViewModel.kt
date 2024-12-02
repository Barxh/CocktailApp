package com.example.coctailapp.ui.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private var _selectedBottomNavigationItem  = MutableStateFlow(0)
    val selectedBottomNavigationItem = _selectedBottomNavigationItem.asStateFlow()

    fun setSelectedBottomNavigationItem(selectedItem: Int){
        _selectedBottomNavigationItem.value = selectedItem
    }


}