package com.example.coctailapp.ui.screens.main.content.shopping

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.coctailapp.R
import com.example.coctailapp.model.localdb.ShoppingListIngredient
import com.example.coctailapp.ui.navigation.Destinations
import com.example.coctailapp.ui.screens.main.MainViewModel
import com.example.coctailapp.ui.screens.main.content.cocktails.cocktails_details.CocktailsDetailsScreen
import com.example.coctailapp.ui.theme.PrimaryColor
import com.example.coctailapp.ui.theme.SecondaryColor
import com.example.coctailapp.ui.theme.TertiaryColor
import com.example.coctailapp.ui.theme.Typography


@Composable
fun ShoppingContent(email: String, mainViewModel: MainViewModel = hiltViewModel()) {


    val navController = rememberNavController()
    mainViewModel.setNestedShoppingNavController(navController)
    ShoppingScreenNavigation(email, navController)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    email: String,
    navHostController: NavHostController,
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel()
) {
    shoppingListViewModel.getUserShoppingList(email)
    val shoppingList = shoppingListViewModel.shoppingListByRecipe.collectAsStateWithLifecycle()
    val unwantedIngredients =
        shoppingListViewModel.unwantedIngredients.collectAsStateWithLifecycle()
    val showDialog = shoppingListViewModel.showDialog.collectAsStateWithLifecycle()
    when (showDialog.value) {
        ShoppingListDialogEvent.DialogDismissedEvent -> {
            //Do nothing
        }

        ShoppingListDialogEvent.PendingDialogEvent -> {
            DeleteShoppingListDialog(email)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.shoppingList),
                        style = Typography.bodyLarge,
                        fontSize = TextUnit(22f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SecondaryColor),
                actions = {
                    IconButton(
                        onClick = {
                            shoppingListViewModel.showAlertDialog()
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (unwantedIngredients.value.isNotEmpty())
                FloatingActionButton(
                    onClick = {
                        shoppingListViewModel.deleteUnwantedIngredients()
                    },
                    containerColor = TertiaryColor,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize()) {
            if (shoppingList.value.isEmpty()) {
                Text(
                    text = "There are no ingredients on you shopping list",
                    modifier = Modifier.align(
                        Alignment.Center
                    ),
                    textAlign = TextAlign.Center
                )
            } else {

                LazyColumn(
                    Modifier
                        .padding(top = innerPadding.calculateTopPadding() + 10.dp)
                        .align(Alignment.TopCenter)
                        .fillMaxSize()
                ) {
                    for (shoppingListByRecipe in shoppingList.value) {
                        item {
                            Column(
                                Modifier
                                    .padding(start = 20.dp)
                                    .clickable {
                                        navHostController.navigate(
                                            Destinations.CocktailsDetailsScreen(
                                                shoppingListByRecipe.recipeId
                                            )
                                        )
                                    }) {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 5.dp)
                                ) {
                                    Text(
                                        shoppingListByRecipe.recipeName,
                                        Modifier
                                            .align(Alignment.CenterStart)
                                            .padding(start = 5.dp, end = 5.dp),
                                        Color.Gray
                                    )
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward),
                                        null,
                                        Modifier.align(
                                            Alignment.CenterEnd
                                        ),
                                        Color.Gray
                                    )


                                }
                                HorizontalDivider(
                                    Modifier.fillMaxWidth(),
                                    1.dp,
                                    Color.Gray
                                )
                            }
                        }
                        items(shoppingListByRecipe.neededIngredient) { ingredient ->
                            Row(
                                Modifier.padding(start = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = unwantedIngredients.value.contains(
                                        ShoppingListIngredient(
                                            email,
                                            shoppingListByRecipe.recipeId,
                                            "",
                                            ingredient.ingredientsName,
                                            ""
                                        )
                                    ),
                                    onCheckedChange = {
                                        shoppingListViewModel.addOrRemoveUnwantedIngredient(
                                            ingredient.ingredientsName,
                                            shoppingListByRecipe.recipeId,
                                            email
                                        )
                                    },
                                    colors = CheckboxDefaults.colors(
                                        PrimaryColor,
                                        PrimaryColor,
                                        Color.White
                                    )
                                )
                                Text(
                                    text = "${ingredient.ingredientsName} ${ingredient.ingredientMeasure}",
                                    modifier = Modifier.padding(start = 10.dp),
                                    textDecoration = if (unwantedIngredients.value.contains(
                                            ShoppingListIngredient(
                                                email,
                                                shoppingListByRecipe.recipeId,
                                                "",
                                                ingredient.ingredientsName,
                                                ""
                                            )
                                        )
                                    ) TextDecoration.LineThrough
                                    else TextDecoration.None
                                )
                            }

                        }
                    }


                }

            }

        }
    }
}

@Composable
fun ShoppingScreenNavigation(email: String, navHostController: NavHostController) {

    NavHost(navHostController, Destinations.ShoppingFragment) {
        composable<Destinations.ShoppingFragment> {
            ShoppingScreen(email, navHostController)
        }
        composable<Destinations.CocktailsDetailsScreen> {
            val args = it.toRoute<Destinations.CocktailsDetailsScreen>()
            CocktailsDetailsScreen(email, args.cocktailId, {
                navHostController.navigate(Destinations.ShoppingFragment) {
                    popUpTo(Destinations.ShoppingFragment) {
                        inclusive = true
                    }
                }
            })
        }

    }
}