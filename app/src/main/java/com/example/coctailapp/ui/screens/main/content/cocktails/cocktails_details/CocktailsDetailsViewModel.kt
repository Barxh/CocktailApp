package com.example.coctailapp.ui.screens.main.content.cocktails.cocktails_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.example.coctailapp.R
import com.example.coctailapp.database.FavoritesCocktailsDao
import com.example.coctailapp.database.ShoppingListDao
import com.example.coctailapp.model.dto.CocktailDetailsDto
import com.example.coctailapp.model.Ingredient
import com.example.coctailapp.model.localdb.ShoppingListIngredient
import com.example.coctailapp.model.localdb.UserFavoriteCocktail
import com.example.coctailapp.network.CocktailsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class CocktailsDetailsViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val cocktailsApi: CocktailsApi,
    private val shoppingListDao: ShoppingListDao,
    private val favoritesCocktailsDao: FavoritesCocktailsDao
) : ViewModel() {

    private var _cocktailDetailFetchingStatus =
        MutableStateFlow<CocktailDetailsFetchingStatus>(CocktailDetailsFetchingStatus.LoadingEvent)
    val cocktailDetailsFetchingStatus = _cocktailDetailFetchingStatus.asStateFlow()

    private lateinit var favoriteCocktailFlow: Flow<List<UserFavoriteCocktail>>
    var isFavoriteCocktail: StateFlow<List<UserFavoriteCocktail>> =
        MutableStateFlow(emptyList())

    @Inject lateinit var cocktailDetails: CocktailDetailsDto

    private lateinit var _shoppingList: Flow<List<ShoppingListIngredient>>

    private var _listOfIngredientsWithShoppingList = MutableStateFlow<List<Ingredient>>(emptyList())
    val listOfIngredientsWithShoppingList = _listOfIngredientsWithShoppingList.asStateFlow()


    fun setShoppingList(email: String, cocktailId : String) {
        _shoppingList =
            shoppingListDao.getUserShoppingListIngredientsByCocktailId(
                email,
                cocktailId
            )
        _cocktailDetailFetchingStatus.combine(_shoppingList) { fetchingStatus, shoppingList ->
            when (fetchingStatus) {
                is CocktailDetailsFetchingStatus.ErrorEvent -> {

                    //Do nothing
                }

                CocktailDetailsFetchingStatus.LoadingEvent -> {
                    //Do nothing
                }

                is CocktailDetailsFetchingStatus.SuccessEvent -> {

                    val list = mutableListOf<Ingredient>()
                    for (ingredient in fetchingStatus.cocktailDetails.getListOfIngredients()) {
                        list.add(
                            Ingredient(
                                ingredient.ingredientsName,
                                ingredient.ingredientMeasure,
                                isNeededIngredient(ingredient, shoppingList)
                            )
                        )
                    }
                    _listOfIngredientsWithShoppingList.value = list
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun isNeededIngredient(
        ingredient: Ingredient,
        shoppingList: List<ShoppingListIngredient>
    ): Boolean {
        for (shoppingIngredient in shoppingList) {
            if (shoppingIngredient.cocktailIngredientName == ingredient.ingredientsName)
                return true
        }
        return false

    }

    fun handleFavoriteButtonToggle(email: String) {

        viewModelScope.launch {
            if (isFavoriteCocktail.value.isNotEmpty()) {
                favoritesCocktailsDao.deleteFavoriteCocktail(
                    UserFavoriteCocktail(
                        userId = email,
                        idDrink = cocktailDetails.idDrink,
                        cocktailName = cocktailDetails.strDrink,
                        imageURL = cocktailDetails.strDrinkThumb
                    )
                )
            } else {
                favoritesCocktailsDao.insertFavoriteCocktail(
                    UserFavoriteCocktail(
                        userId = email,
                        idDrink = cocktailDetails.idDrink,
                        cocktailName = cocktailDetails.strDrink,
                        imageURL = cocktailDetails.strDrinkThumb
                    )
                )
            }

        }

    }

    fun setIsFavoriteCocktailStateFlow(id: String) {
        favoriteCocktailFlow =
            favoritesCocktailsDao.getUserFavoriteCocktailById(cocktailId = id)

            isFavoriteCocktail = favoriteCocktailFlow.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    }

    fun getCocktailDetails(id: String) {

        viewModelScope.launch {

            try {
                _cocktailDetailFetchingStatus.value = CocktailDetailsFetchingStatus.LoadingEvent
                val response = cocktailsApi.getCocktailDetails(id)
                if (response.drinks != null) {
                    _cocktailDetailFetchingStatus.value =
                        CocktailDetailsFetchingStatus.SuccessEvent(response.drinks[0])
                    cocktailDetails = response.drinks[0]
                } else {
                    _cocktailDetailFetchingStatus.value =
                        CocktailDetailsFetchingStatus.ErrorEvent(context.getString(R.string.cocktailsDetailsNotFound))
                }


            } catch (e: IOException) {
                _cocktailDetailFetchingStatus.value = CocktailDetailsFetchingStatus.ErrorEvent(
                    context.getString(
                        R.string.internetErrorMessage
                    )
                )
            } catch (e: HttpException) {
                _cocktailDetailFetchingStatus.value =
                    CocktailDetailsFetchingStatus.ErrorEvent(context.getString(R.string.httpErrorMessage))
            }
        }
    }

    fun insertOrDeleteNeededIngredient(ingredient: Ingredient, email: String) {

        viewModelScope.launch {
            if (ingredient.isInShoppingList) {
                shoppingListDao.deleteShoppingListIngredient(
                    ShoppingListIngredient(
                        email, cocktailDetails.idDrink, cocktailDetails.strDrink,
                        ingredient.ingredientsName, ingredient.ingredientMeasure
                    )
                )
            } else
                shoppingListDao.insertShoppingListIngredient(
                    ShoppingListIngredient(
                        email, cocktailDetails.idDrink, cocktailDetails.strDrink,
                        ingredient.ingredientsName, ingredient.ingredientMeasure
                    )
                )
        }
    }


}