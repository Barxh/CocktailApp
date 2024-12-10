package com.example.coctailapp.network

import com.example.coctailapp.model.dto.CocktailResponseDto
import com.example.coctailapp.model.dto.CocktailsDetailsResponseDto
import com.example.coctailapp.model.dto.FilterAlcoholicResponseDto
import com.example.coctailapp.model.dto.FilterCategoryResponseDto
import com.example.coctailapp.model.dto.FilterGlassResponseDto
import com.example.coctailapp.model.dto.FilterIngredientResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsApi {


    @GET("filter.php")
    suspend fun getCocktailsByAlcoholic(@Query("a") alcoholic: String): CocktailResponseDto

    @GET("filter.php")
    suspend fun getCocktailsByCategory(@Query("c") alcoholic: String): CocktailResponseDto

    @GET("filter.php")
    suspend fun getCocktailsByGlass(@Query("g") alcoholic: String): CocktailResponseDto

    @GET("filter.php")
    suspend fun getCocktailsByIngredient(@Query("i") alcoholic: String): CocktailResponseDto

    @GET("search.php")
    suspend fun getCocktailsByFirstLetter(@Query("f") alcoholic: String): CocktailsDetailsResponseDto

    @GET("search.php")
    suspend fun getCocktailsWithSearch(@Query("s") alcoholic: String): CocktailsDetailsResponseDto


    @GET("list.php?a=list")
    suspend fun getFilterAlcoholic(): FilterAlcoholicResponseDto

    @GET("list.php?c=list")
    suspend fun getFilterCategory(): FilterCategoryResponseDto

    @GET("list.php?g=list")
    suspend fun getFilterGlass(): FilterGlassResponseDto

    @GET("list.php?i=list")
    suspend fun getFilterIngredient(): FilterIngredientResponseDto

    @GET("lookup.php")
    suspend fun getCocktailDetails(@Query("i") id: String) : CocktailsDetailsResponseDto
}