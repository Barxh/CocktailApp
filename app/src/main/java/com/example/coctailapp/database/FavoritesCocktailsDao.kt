package com.example.coctailapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.coctailapp.model.UserFavoriteCocktail
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesCocktailsDao {

    @Insert
    suspend fun insertFavoriteCocktail(favoriteCocktail: UserFavoriteCocktail)

    @Delete
    suspend fun deleteFavoriteCocktail(favoriteCocktail: UserFavoriteCocktail)

    @Query("SELECT * FROM FAVORITE_COCKTAIL WHERE userId = :email")
    fun getUserFavoritesCocktails(email: String): Flow<List<UserFavoriteCocktail>>

    @Query("SELECT * FROM FAVORITE_COCKTAIL WHERE idDrink = :cocktailId")
    fun getUserFavoriteCocktailById(cocktailId: String): Flow<List<UserFavoriteCocktail>>

}