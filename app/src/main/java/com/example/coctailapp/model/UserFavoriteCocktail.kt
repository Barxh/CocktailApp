package com.example.coctailapp.model

import androidx.room.Entity


@Entity(tableName = "FAVORITE_COCKTAIL", primaryKeys = ["userId", "idDrink"])
data class
UserFavoriteCocktail(
    val userId : String,
    override val idDrink : String,
    val cocktailName : String,
    val imageURL : String

) : FavoritesCocktailInterface {
    override fun equals(other: Any?): Boolean =
        other is FavoritesCocktailInterface && other.idDrink == this.idDrink

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + idDrink.hashCode()
        result = 31 * result + cocktailName.hashCode()
        result = 31 * result + imageURL.hashCode()
        return result
    }


}
