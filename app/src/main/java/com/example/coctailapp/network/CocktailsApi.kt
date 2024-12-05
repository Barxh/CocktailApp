package com.example.coctailapp.network

import com.example.coctailapp.model.CocktailResponse
import com.example.coctailapp.model.CocktailsDetailsResponse
import com.example.coctailapp.model.FilterAlcoholicResponse
import com.example.coctailapp.model.FilterCategoryResponse
import com.example.coctailapp.model.FilterGlassResponse
import com.example.coctailapp.model.FilterIngredientResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsApi {


    @GET("filter.php")
    suspend fun getCocktailsByAlcoholic(@Query("a") alcoholic: String): CocktailResponse

    @GET("filter.php")
    suspend fun getCocktailsByCategory(@Query("c") alcoholic: String): CocktailResponse

    @GET("filter.php")
    suspend fun getCocktailsByGlass(@Query("g") alcoholic: String): CocktailResponse

    @GET("filter.php")
    suspend fun getCocktailsByIngredient(@Query("i") alcoholic: String): CocktailResponse

    @GET("search.php")
    suspend fun getCocktailsByFirstLetter(@Query("f") alcoholic: String): CocktailsDetailsResponse

    @GET("list.php?a=list")
    suspend fun getFilterAlcoholic(): FilterAlcoholicResponse

    @GET("list.php?c=list")
    suspend fun getFilterCategory(): FilterCategoryResponse

    @GET("list.php?g=list")
    suspend fun getFilterGlass(): FilterGlassResponse

    @GET("list.php?i=list")
    suspend fun getFilterIngredient(): FilterIngredientResponse
}