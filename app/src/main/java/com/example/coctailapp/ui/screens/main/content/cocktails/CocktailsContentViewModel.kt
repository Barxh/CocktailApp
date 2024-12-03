package com.example.coctailapp.ui.screens.main.content.cocktails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.example.coctailapp.R
import com.example.coctailapp.network.CocktailsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class CocktailsContentViewModel @Inject constructor(private val cocktailsApi: CocktailsApi,
    @ApplicationContext val context : Context): ViewModel() {




    private var _dataFetchingState = MutableStateFlow<CocktailsFetchingEvent>(CocktailsFetchingEvent.LoadingEvent)
    val dataFetchingEvent = _dataFetchingState.asStateFlow()

    init {
        getCocktailsByAlcoholic(INITIAL_FILTER)
    }



    fun getCocktailsByAlcoholic(alcoholic : String){


        viewModelScope.launch {

            try {
                _dataFetchingState.value = CocktailsFetchingEvent.LoadingEvent
                val response = cocktailsApi.getCocktailsByAlcoholic(alcoholic).drinks
                if(response.isNotEmpty()) {
                    _dataFetchingState.value = CocktailsFetchingEvent.SuccessEvent(response)
                }else{
                    _dataFetchingState.value = CocktailsFetchingEvent.ErrorEvent(
                        context.getString(R.string.emptyCocktailsListErrorMessage))

                }

            }catch (e : IOException){

                _dataFetchingState.value =
                    CocktailsFetchingEvent.ErrorEvent(context.getString(R.string.internetErrorMessage))
            }catch (e: HttpException){
                _dataFetchingState.value =
                    CocktailsFetchingEvent.ErrorEvent(context.getString(R.string.httpErrorMessage))

            }

        }

    }

    // TODO
    fun retryCocktailsPreviewFetching(){


        viewModelScope.launch {
            delay(5000)
            getCocktailsByAlcoholic(INITIAL_FILTER)
        }
    }
    companion object{
        const val INITIAL_FILTER = "Alcoholic"
    }


}