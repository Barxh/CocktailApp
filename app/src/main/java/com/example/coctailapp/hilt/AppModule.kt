package com.example.coctailapp.hilt

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.coctailapp.Constants
import com.example.coctailapp.database.CocktailsDatabase
import com.example.coctailapp.database.FavoritesCocktailsDao
import com.example.coctailapp.database.ShoppingListDao
import com.example.coctailapp.network.CocktailsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("authentication", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideCocktailsApi(): CocktailsApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create()


    @Provides
    @Singleton
    fun provideCocktailDetail(@ApplicationContext context: Context): CocktailsDatabase =
        Room.databaseBuilder(
            context,
            CocktailsDatabase::class.java,
            "cocktails.DB"
        ).fallbackToDestructiveMigration().build()
    @Provides
    @Singleton
    fun provideFavoritesCocktailsDao(cocktailsDatabase: CocktailsDatabase): FavoritesCocktailsDao =
        cocktailsDatabase.favoritesCocktailsDao()

    @Provides
    @Singleton
    fun provideShoppingListDao(cocktailsDatabase: CocktailsDatabase): ShoppingListDao =
        cocktailsDatabase.shoppingListDao()


    @Provides
    @Singleton
    fun provideEmptyString(): String = ""
    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver = context.contentResolver
}