package com.example.coctailapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coctailapp.model.UserFavoriteCocktail


@Database([UserFavoriteCocktail::class], version = 1)
abstract class CocktailsDatabase : RoomDatabase(){

    abstract fun favoritesCocktailsDao(): FavoritesCocktailsDao

}