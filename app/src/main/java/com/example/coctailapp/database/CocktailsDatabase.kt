package com.example.coctailapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coctailapp.model.localdb.ShoppingListIngredient
import com.example.coctailapp.model.localdb.UserFavoriteCocktail


@Database([UserFavoriteCocktail::class, ShoppingListIngredient::class], version = 2)
abstract class CocktailsDatabase : RoomDatabase(){

    abstract fun favoritesCocktailsDao(): FavoritesCocktailsDao
    abstract fun shoppingListDao(): ShoppingListDao

}