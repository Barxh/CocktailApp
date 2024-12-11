package com.example.coctailapp.ui.screens.main.content.cocktails.cocktails_details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.coctailapp.R
import com.example.coctailapp.ui.screens.main.content.cocktails.ErrorScreen
import com.example.coctailapp.ui.screens.main.content.cocktails.LoadingScreen
import com.example.coctailapp.ui.theme.PrimaryColor
import com.example.coctailapp.ui.theme.SecondaryColor
import com.example.coctailapp.ui.theme.Typography


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailsDetailsScreen(
    email: String,
    cocktailId: String,
    backButtonNavigation: ()-> Unit,
    cocktailsDetailsViewModel: CocktailsDetailsViewModel = hiltViewModel()
) {
    cocktailsDetailsViewModel.setShoppingList(email, cocktailId)
    cocktailsDetailsViewModel.setIsFavoriteCocktailStateFlow(cocktailId)
    val cocktailDetailsFetchingStatus =
        cocktailsDetailsViewModel.cocktailDetailsFetchingStatus.collectAsStateWithLifecycle()

    val ingredientsListWithShoppingList =
        cocktailsDetailsViewModel.listOfIngredientsWithShoppingList.collectAsStateWithLifecycle()


    val isFavoriteCocktail =
        cocktailsDetailsViewModel.isFavoriteCocktail.collectAsStateWithLifecycle()


    cocktailsDetailsViewModel.getCocktailDetails(id = cocktailId)

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(stringResource(R.string.searchCocktail), style = Typography.bodyLarge)
        }, navigationIcon = {
            IconButton(onClick = {backButtonNavigation()}) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                    null,
                    tint = Color.Black
                )

            }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = SecondaryColor))
    }) {
        Column {


            when (cocktailDetailsFetchingStatus.value) {
                is CocktailDetailsFetchingStatus.ErrorEvent ->
                    ErrorScreen((cocktailDetailsFetchingStatus.value as CocktailDetailsFetchingStatus.ErrorEvent).errorMessage)

                CocktailDetailsFetchingStatus.LoadingEvent -> LoadingScreen()
                is CocktailDetailsFetchingStatus.SuccessEvent -> {


                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            AsyncImage(
                                model = (cocktailDetailsFetchingStatus.value as CocktailDetailsFetchingStatus.SuccessEvent)
                                    .cocktailDetails.strDrinkThumb,
                                contentDescription = stringResource(R.string.cocktailImageDescription),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp).padding(top = 30.dp, bottom = 10.dp),
                                contentScale = ContentScale.Crop,
                            )
                            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        (cocktailDetailsFetchingStatus.value as CocktailDetailsFetchingStatus.SuccessEvent)
                                            .cocktailDetails.strDrink,
                                        modifier = Modifier.align(Alignment.CenterStart),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = TextUnit(26f, TextUnitType.Sp)
                                    )

                                    IconButton(onClick = {
                                        cocktailsDetailsViewModel.handleFavoriteButtonToggle(email)
                                    }, Modifier.align(Alignment.CenterEnd)) {
                                        Box {
                                            if (isFavoriteCocktail.value.isNotEmpty()) {

                                                Icon(
                                                    imageVector = ImageVector.vectorResource(R.drawable.circular_button_background),
                                                    null,
                                                    tint = Color.Red,
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                                Icon(
                                                    imageVector = ImageVector.vectorResource(R.drawable.ic_favorite_filled),
                                                    null,
                                                    tint = Color.White,
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = ImageVector.vectorResource(R.drawable.circular_button_background),
                                                    null,
                                                    tint = Color.Gray,
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                                Icon(
                                                    imageVector = ImageVector.vectorResource(R.drawable.ic_favorite_outlined),
                                                    null,
                                                    tint = Color.Black,
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            }

                                        }
                                    }
                                }

                                Row(modifier = Modifier.padding(bottom = 15.dp)) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_alcoholic),
                                        contentDescription = null,
                                        tint = PrimaryColor,
                                        modifier = Modifier.padding(end = 10.dp)
                                    )
                                    Text(
                                        (cocktailDetailsFetchingStatus.value as CocktailDetailsFetchingStatus.SuccessEvent)
                                            .cocktailDetails.strAlcoholic, fontWeight = FontWeight.Bold
                                    )
                                }
                                Row(modifier = Modifier.padding(bottom = 15.dp)) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_category),
                                        contentDescription = null,
                                        tint = PrimaryColor,
                                        modifier = Modifier.padding(end = 10.dp)
                                    )
                                    Text(stringResource(R.string.category) + ":  " , fontWeight = FontWeight.Bold)

                                    Text(
                                        (cocktailDetailsFetchingStatus.value as CocktailDetailsFetchingStatus.SuccessEvent)
                                            .cocktailDetails.strCategory,
                                        Modifier
                                            .background(PrimaryColor)
                                            .padding(
                                                top = 2.dp,
                                                bottom = 2.dp,
                                                start = 7.dp,
                                                end = 7.dp
                                            ), color = Color.White

                                    )
                                }
                                Row (modifier = Modifier.padding(bottom = 15.dp)){
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_cocktail),
                                        contentDescription = null,
                                        tint = PrimaryColor
                                    )
                                    Text(stringResource(R.string.glassType) + ": ", fontWeight = FontWeight.Bold)
                                    Text(
                                        (cocktailDetailsFetchingStatus.value as CocktailDetailsFetchingStatus.SuccessEvent)
                                            .cocktailDetails.strGlass,
                                        modifier = Modifier.padding(start = 10.dp)
                                            .background(PrimaryColor)
                                            .padding(
                                                top = 2.dp,
                                                bottom = 2.dp,
                                                start = 7.dp,
                                                end = 7.dp
                                            )

                                        , color = Color.White
                                    )
                                }


                                Text(
                                    stringResource(R.string.ingredients),
                                    fontSize = TextUnit(20f, TextUnitType.Sp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                            items(
                                ingredientsListWithShoppingList.value
                            ) { ingredient ->

                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 20.dp)) {
                                    Checkbox(
                                        checked = ingredient.isInShoppingList,
                                        onCheckedChange = {

                                            cocktailsDetailsViewModel.insertOrDeleteNeededIngredient(
                                                ingredient,
                                                email
                                            )
                                        },
                                        colors = CheckboxDefaults.colors(
                                            PrimaryColor,
                                            PrimaryColor,
                                            Color.White
                                        )

                                    )
                                    if (ingredient.ingredientMeasure != null)
                                        Text(ingredient.ingredientsName + " (" + ingredient.ingredientMeasure + ")")
                                    else
                                        Text(ingredient.ingredientsName)

                                }
                            }
                            item {
                                Text(
                                    stringResource(R.string.instructions),
                                    fontSize = TextUnit(20f, TextUnitType.Sp),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 20.dp)
                                )
                                Text(
                                    (cocktailDetailsFetchingStatus.value as CocktailDetailsFetchingStatus.SuccessEvent)
                                        .cocktailDetails.strInstructions,
                                    modifier = Modifier.padding(bottom = 40.dp, start = 25.dp)
                                )

                            }
                        }
                    }
                }
            }
        }
    }




