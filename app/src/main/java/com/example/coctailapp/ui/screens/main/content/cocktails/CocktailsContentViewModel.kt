package com.example.coctailapp.ui.screens.main.content.cocktails

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.example.coctailapp.R
import com.example.coctailapp.model.CocktailResponse
import com.example.coctailapp.network.CocktailsApi
import com.example.coctailapp.ui.screens.main.content.cocktails.filter.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class CocktailsContentViewModel @Inject constructor(
    private val cocktailsApi: CocktailsApi,
    @ApplicationContext val context: Context
) : ViewModel() {

    private var _dataFetchingState =
        MutableStateFlow<CocktailsFetchingEvent>(CocktailsFetchingEvent.LoadingEvent)
    val dataFetchingEvent = _dataFetchingState.asStateFlow()

    private var filter = INITIAL_FILTER
    private var filterType = FilterType.ALCOHOLIC_OR_NOT

    fun setFilterType(filter: FilterType) {
        this.filterType = filter
    }
    fun getFilterType(): FilterType = filterType

    fun setFilter(filter: String) {
        this.filter = filter
    }

    fun getFilter(): String = filter


    fun filterCocktails() {


        viewModelScope.launch {

            try {

                _dataFetchingState.value = CocktailsFetchingEvent.LoadingEvent
                val response: CocktailResponse = when (filterType) {
                    FilterType.ALCOHOLIC_OR_NOT -> cocktailsApi.getCocktailsByAlcoholic(filter)
                    FilterType.CATEGORY -> cocktailsApi.getCocktailsByCategory(filter)
                    FilterType.GLASS_USED -> cocktailsApi.getCocktailsByGlass(filter)
                    FilterType.INGREDIENT -> cocktailsApi.getCocktailsByIngredient(filter)
                    FilterType.FIRST_LETTER -> cocktailsApi.getCocktailsByFirstLetter(filter).toCocktailsResponse()
                }
                Log.e("Bug", response.toString())
                if (response.drinks!=null)
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