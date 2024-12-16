package com.example.coctailapp.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.coctailapp.ui.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private var nestedHostNavController : NavHostController? = null
    private var nestedShoppingNavController : NavHostController? = null
    private var _selectedBottomNavigationItem  = MutableStateFlow(0)
    val selectedBottomNavigationItem = _selectedBottomNavigationItem.asStateFlow()

    fun setSelectedBottomNavigationItem(selectedItem: Int){
        _selectedBottomNavigationItem.value = selectedItem
    }

    fun setNestedHostNavController(nestedNavHostController: NavHostController){
        nestedHostNavController = nestedNavHostController
    }
    fun setNestedShoppingNavController(nestedNavHostController: NavHostController){
        nestedShoppingNavController = nestedNavHostController
    }
    fun resetNestedHomeNavController(){
        nestedHostNavController?.navigate(Destinations.CocktailsFragment){
            popUpTo(Destinations.CocktailsFragment){
                inclusive = true
            }
        }
    }
    fun resetNestedShoppingNavController(){
        nestedShoppingNavController?.navigate(Destinations.ShoppingFragment){
            popUpTo(Destinations.ShoppingFragment){
                inclusive = true
            }
        }

    }

    fun setHomeContent() {
        _selectedBottomNavigationItem.value = 0
    }


}