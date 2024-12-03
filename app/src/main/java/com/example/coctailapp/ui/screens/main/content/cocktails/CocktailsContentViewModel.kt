package com.example.coctailapp.ui.screens.main.content.cocktails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.example.coctailapp.R
import com.example.coctailapp.model.CocktailsPreview
import com.example.coctailapp.network.CocktailsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class CocktailsContentViewModel @Inject constructor(private val cocktailsApi: CocktailsApi,
    @ApplicationContext val context : Context): ViewModel() {

    private var _cocktailsList = MutableStateFlow(emptyList<CocktailsPreview>())
    val cocktailsList = _cocktailsList.asStateFlow()


    private var _dataFetchingState = MutableStateFlow<CocktailsFetchingEvent>(CocktailsFetchingEvent.LoadingEvent)
    val dataFetchingEvent = _dataFetchingState.asStateFlow()

    init {
        getCocktailsByAlcoholic("Alcoholic")
    }



    fun getCocktailsByAlcoholic(alcoholic : String){
        _dataFetchingState.value = CocktailsFetchingEvent.LoadingEvent
        viewModelScope.launch {

            try {
                val response = cocktailsApi.getCocktailsByAlcoholic(alcoholic).drinks
                if(response.isNotEmpty()) {
                    _dataFetchingState.value = CocktailsFetchingEvent.SuccessEvent
                    _cocktailsList.value = response
                }else{
                    _dataFetchingState.value = CocktailsFetchingEvent.ErrorEvent(
                        context.getString(R.string.emptyCocktailsListErrorMessage))

                }

            }catch (e : IOException){

                CocktailsFetchingEvent.ErrorEvent(context.getString(R.string.internetErrorMessage))

            }catch (e: HttpException){
                CocktailsFetchingEvent.ErrorEvent(context.getString(R.string.httpErrorMessage))

            }

        }

    }


}