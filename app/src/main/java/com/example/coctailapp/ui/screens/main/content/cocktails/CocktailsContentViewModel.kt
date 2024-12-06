package com.example.coctailapp.ui.screens.main.content.cocktails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.example.coctailapp.R
import com.example.coctailapp.database.FavoritesCocktailsDao
import com.example.coctailapp.model.CocktailResponse
import com.example.coctailapp.model.CocktailsPreview
import com.example.coctailapp.model.CocktailsPreviewPlusFavorites
import com.example.coctailapp.model.FavoritesCocktailInterface
import com.example.coctailapp.model.UserFavoriteCocktail
import com.example.coctailapp.network.CocktailsApi
import com.example.coctailapp.ui.screens.main.content.cocktails.filter.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class CocktailsContentViewModel @Inject constructor(
    private val cocktailsApi: CocktailsApi,
    @ApplicationContext val context: Context,
    private val favoritesCocktailsDao: FavoritesCocktailsDao
) : ViewModel() {

    private var _dataFetchingState =
        MutableStateFlow<CocktailsFetchingEvent>(CocktailsFetchingEvent.LoadingEvent)
    val dataFetchingEvent = _dataFetchingState.asStateFlow()

    private var _cocktailsPreviewPlusFavorites =
        MutableStateFlow(listOf<CocktailsPreviewPlusFavorites>())
    val cocktailsPreviewPlusFavorites = _cocktailsPreviewPlusFavorites.asStateFlow()


    private lateinit var _favoritesList: Flow<List<UserFavoriteCocktail>>


    private var filter = INITIAL_FILTER
    private var filterType = FilterType.ALCOHOLIC_OR_NOT
    private var temporaryFilterType = FilterType.ALCOHOLIC_OR_NOT
    private var email = ""


    fun setCurrentUserEmail(email: String) {
        this.email = email

        _favoritesList = favoritesCocktailsDao.getUserFavoritesCocktails(email)
        _dataFetchingState.combine(_favoritesList) { fetchingStatus, favorites ->
            when (fetchingStatus) {
                is CocktailsFetchingEvent.ErrorEvent -> {
                    // Do nothing
                }

                CocktailsFetchingEvent.LoadingEvent -> {
                    // Do nothing
                }

                is CocktailsFetchingEvent.SuccessEvent -> {
                    val list = mutableListOf<CocktailsPreviewPlusFavorites>()

                    for (cocktailPreview: CocktailsPreview in fetchingStatus.cocktailsList) {
                        list.add(
                            CocktailsPreviewPlusFavorites(
                                idDrink = cocktailPreview.idDrink,
                                cocktailName = cocktailPreview.strDrink,
                                imageURL = cocktailPreview.strDrinkThumb,
                                favorite = isFavorite(favorites, cocktailPreview)
                            )
                        )
                    }

                    _cocktailsPreviewPlusFavorites.value = list
                }


            }

        }.launchIn(viewModelScope)


    }

    private fun isFavorite(
        arrayOfFavorite: List<FavoritesCocktailInterface>,
        subject : FavoritesCocktailInterface
    ): Boolean {

        for (favorite in arrayOfFavorite){
            if (favorite.idDrink == subject.idDrink)
                return true
        }
        return false
    }

    fun setFilterType(filter: FilterType) {
        temporaryFilterType = filter
    }

    fun getFilterType(): FilterType = temporaryFilterType

    fun setFilter(filter: String) {
        filterType = temporaryFilterType
        this.filter = filter
    }

    fun getFilter(): String = filter

    fun deleteOrInsertToUserFavorites(
        cocktailsPreviewPlusFavorites: CocktailsPreviewPlusFavorites
    ) {
        val favorite = UserFavoriteCocktail(
            email,
            cocktailsPreviewPlusFavorites.idDrink,
            cocktailsPreviewPlusFavorites.cocktailName,
            cocktailsPreviewPlusFavorites.imageURL
        )
        viewModelScope.launch {
            if (cocktailsPreviewPlusFavorites.favorite)
                favoritesCocktailsDao.deleteFavoriteCocktail(favorite)
            else
                favoritesCocktailsDao.insertFavoriteCocktail(favorite)
        }
    }

    fun filterCocktails() {


        viewModelScope.launch {

            try {

                _dataFetchingState.value = CocktailsFetchingEvent.LoadingEvent
                val response: CocktailResponse = when (filterType) {
                    FilterType.ALCOHOLIC_OR_NOT -> cocktailsApi.getCocktailsByAlcoholic(filter)
                    FilterType.CATEGORY -> cocktailsApi.getCocktailsByCategory(filter)
                    FilterType.GLASS_USED -> cocktailsApi.getCocktailsByGlass(filter)
                    FilterType.INGREDIENT -> cocktailsApi.getCocktailsByIngredient(filter)
                    FilterType.FIRST_LETTER -> cocktailsApi.getCocktailsByFirstLetter(filter)
                        .toCocktailsResponse()
                }
                if (response.drinks != null)
                    _dataFetchingState.value = CocktailsFetchingEvent.SuccessEvent(response.drinks)
                else {
                    _dataFetchingState.value =
                        CocktailsFetchingEvent.ErrorEvent(context.getString(R.string.emptyCocktailsListErrorMessage))
                }

            } catch (e: IOException) {

                _dataFetchingState.value =
                    CocktailsFetchingEvent.ErrorEvent(context.getString(R.string.internetErrorMessage))
            } catch (e: HttpException) {
                _dataFetchingState.value =
                    CocktailsFetchingEvent.ErrorEvent(context.getString(R.string.httpErrorMessage))

            }catch (e : IllegalStateException){
                //hello
            }

        }

    }

    // TODO
    fun retryCocktailsPreviewFetching() {


        viewModelScope.launch {
            delay(5000)
            filterCocktails()
        }
    }

    companion object {
        const val INITIAL_FILTER = "Alcoholic"
    }


}