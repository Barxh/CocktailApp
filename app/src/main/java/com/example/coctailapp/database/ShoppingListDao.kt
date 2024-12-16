package com.example.coctailapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.coctailapp.model.localdb.ShoppingListIngredient
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Insert
    suspend fun insertShoppingListIngredient(ingredient: ShoppingListIngredient)

    @Delete
    suspend fun deleteShoppingListIngredient(ingredient: ShoppingListIngredient)

    @Delete
    suspend fun deleteListOfShoppingIngredients(listOfShoppingIngredients: List<ShoppingListIngredient>)

    @Query("SELECT * FROM SHOPPING_LIST_INGREDIENT WHERE USERID = :userId AND COCKTAILID = :cocktailId")
    fun getUserShoppingListIngredientsByCocktailId(userId: String, cocktailId: String) : Flow<List<ShoppingListIngredient>>

    @Query("SELECT * FROM SHOPPING_LIST_INGREDIENT WHERE USERID = :userId ORDER BY COCKTAILID")
    fun getUserShoppingList(userId: String) : Flow<List<ShoppingListIngredient>>

    @Query("DELETE FROM SHOPPING_LIST_INGREDIENT WHERE USERID = :userId")
    suspend fun deleteUserShoppingList(userId: String)

}