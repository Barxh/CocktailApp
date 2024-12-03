package com.example.coctailapp.network

import com.example.coctailapp.model.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsApi {


    @GET("filter.php")
    suspend fun getCocktailsByAlcoholic(@Query("a") alcoholic: String): CocktailResponse
}