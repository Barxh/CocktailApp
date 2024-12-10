package com.example.coctailapp.model.dto

import android.util.Log
import com.example.coctailapp.model.Ingredient

data class CocktailDetailsDto(
    val idDrink: String,
    val strAlcoholic: String,
    val strCategory: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val strGlass: String,
    val strIngredient1: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strInstructions: String,
    val strMeasure1: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?
){
    fun getListOfIngredients() : List<Ingredient>{
        Log.e("Ingredient", this.toString())
        val list = mutableListOf<Ingredient>()
        if(strIngredient1!= null ){
            list.add(Ingredient(strIngredient1, strMeasure1))
        }
        if(strIngredient2!= null ){
            list.add(Ingredient(strIngredient2, strMeasure2))
        }
        if(strIngredient3!= null ){
            list.add(Ingredient(strIngredient3, strMeasure3))
        }
        if(strIngredient4!= null ){
            list.add(Ingredient(strIngredient4, strMeasure4))
        }
        if(strIngredient5!= null ){
            list.add(Ingredient(strIngredient5, strMeasure5))
        }
        if(strIngredient6!= null ){
            list.add(Ingredient(strIngredient6, strMeasure6))
        }
        if(strIngredient7!= null){
            list.add(Ingredient(strIngredient7, strMeasure7))
        }
        if(strIngredient8!= null ){
            list.add(Ingredient(strIngredient8, strMeasure8))
        }
        if(strIngredient9!= null ){
            list.add(Ingredient(strIngredient9, strMeasure9))
        }
        if(strIngredient10!= null ){
            list.add(Ingredient(strIngredient10, strMeasure10))
        }
        if(strIngredient11!= null ){
            list.add(Ingredient(strIngredient11, strMeasure11))
        }
        if(strIngredient12!= null ){
            list.add(Ingredient(strIngredient12, strMeasure12))
        }
        if(strIngredient13!= null ){
            list.add(Ingredient(strIngredient13, strMeasure13))
        }
        if(strIngredient14!= null ){
            list.add(Ingredient(strIngredient14, strMeasure14))
        }
        if(strIngredient15!= null ){
            list.add(Ingredient(strIngredient15, strMeasure15))
        }





        return list
    }
}