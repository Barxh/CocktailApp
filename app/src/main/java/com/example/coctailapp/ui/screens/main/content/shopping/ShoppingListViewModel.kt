package com.example.coctailapp.ui.screens.main.content.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coctailapp.database.ShoppingListDao
import com.example.coctailapp.model.Ingredient
import com.example.coctailapp.model.ShoppingListModel
import com.example.coctailapp.model.localdb.ShoppingListIngredient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(private val shoppingListDao: ShoppingListDao) :
    ViewModel() {


    private lateinit var shoppingListIngredient: Flow<List<ShoppingListIngredient>>
    private var _shoppingListByRecipe = MutableStateFlow<List<ShoppingListModel>>(emptyList())
    val shoppingListByRecipe = _shoppingListByRecipe.asStateFlow()
    private var _unwantedIngredients = MutableStateFlow<List<ShoppingListIngredient>>(emptyList())
    val unwantedIngredients = _unwantedIngredients.asStateFlow()
    private var _showDialog = MutableStateFlow<ShoppingListDialogEvent>(ShoppingListDialogEvent.DialogDismissedEvent)
    val showDialog = _showDialog.asStateFlow()


    fun getUserShoppingList(email: String) {

        shoppingListIngredient = shoppingListDao.getUserShoppingList(email)

        createShoppingListForLazyColumn()

    }


    private fun createShoppingListForLazyColumn() {
        viewModelScope.launch {

            shoppingListIngredient.collectLatest { shoppingListFromFlow ->
                var previousCocktailId = ""
                val shoppingList = mutableListOf<ShoppingListModel>()
                for (shoppingListIngredients in shoppingListFromFlow) {
                    if (previousCocktailId == shoppingListIngredients.cocktailId) {
                        shoppingList.last().neededIngredient.add(
                            Ingredient(
                                shoppingListIngredients.cocktailIngredientName,
                                shoppingListIngredients.cocktailIngredientMeasure
                            )
                        )
                    } else {
                        previousCocktailId = shoppingListIngredients.cocktailId
                        shoppingList.add(
                            ShoppingListModel(
                                shoppingListIngredients.cocktailId,
                                shoppingListIngredients.cocktailName,
                                mutableListOf(
                                    Ingredient(
                                        shoppingListIngredients.cocktailIngredientName,
                                        shoppingListIngredients.cocktailIngredientMeasure
                                    )
                                )
                            )
                        )
                    }
                }

                _shoppingListByRecipe.value = shoppingList
            }

        }
    }


    fun addOrRemoveUnwantedIngredient(ingredientName: String, cocktailId: String, userId: String) {
        val ingredient = ShoppingListIngredient(
            userId,
            cocktailId,
            "",
            ingredientName,
            ""
        )
        if (!_unwantedIngredients.value.contains(ingredient))
            _unwantedIngredients.value += listOf(ingredient)
        else
            _unwantedIngredients.value -= listOf(ingredient)

    }

    fun deleteUnwantedIngredients() {
        viewModelScope.launch {
            shoppingListDao.deleteListOfShoppingIngredients(unwantedIngredients.value)
            _unwantedIngredients.value = emptyList()
        }
    }

    fun deleteAllFromShoppingList(userId: String){
        viewModelScope.launch {
            shoppingListDao.deleteUserShoppingList(userId)
            _shoppingListByRecipe.value = emptyList()
            _unwantedIngredients.value = emptyList()
            cancelDialog()
        }
    }

    fun cancelDialog() {
        _showDialog.value = ShoppingListDialogEvent.DialogDismissedEvent
    }

    fun showAlertDialog() {
        _showDialog.value = ShoppingListDialogEvent.PendingDialogEvent
    }
}