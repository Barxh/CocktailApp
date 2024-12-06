package com.example.coctailapp.ui.screens.main.content.cocktails.filter

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.example.coctailapp.Constants
import com.example.coctailapp.R
import com.example.coctailapp.model.ListConvertible
import com.example.coctailapp.network.CocktailsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val cocktailsApi: CocktailsApi,
    @ApplicationContext val context: Context
) : ViewModel() {

    private var _fetchingState =
        MutableStateFlow<FilterFetchingEvent>(FilterFetchingEvent.LoadingEvent)
    val fetchingState = _fetchingState.asStateFlow()


    fun fetchFilterData(filterType: FilterType) {
        viewModelScope.launch {

            try {

                val response: ListConvertible = when (filterType) {
                    FilterType.ALCOHOLIC_OR_NOT -> cocktailsApi.getFilterAlcoholic()
                    FilterType.CATEGORY -> cocktailsApi.getFilterCategory()
                    FilterType.GLASS_USED -> cocktailsApi.getFilterGlass()
                    FilterType.INGREDIENT -> cocktailsApi.getFilterIngredient()
                    FilterType.FIRST_LETTER ->  object : ListConvertible {
                        override fun toList(): List<String> {
                            return Constants.FIRST_LETTER_LIST
                        }

                    }

                }
                _fetchingState.value = FilterFetchingEvent.SuccessEvent(response.toList())

            } catch (e: IOException) {

                _fetchingState.value =
                    FilterFetchingEvent.ErrorEvent(context.getString(R.string.internetErrorMessage))
            } catch (e: HttpException) {
                _fetchingState.value =
                    FilterFetchingEvent.ErrorEvent(context.getString(R.string.httpErrorMessage))

            }catch (e : IllegalStateException){
                //hello
            }


        }


    }


}