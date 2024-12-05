package com.example.coctailapp.ui.screens.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coctailapp.R
import com.example.coctailapp.model.BottomNavigationItem
import com.example.coctailapp.ui.navigation.Destinations
import com.example.coctailapp.ui.screens.main.content.cocktails.CocktailsScreen
import com.example.coctailapp.ui.screens.main.content.profile.ProfileContent
import com.example.coctailapp.ui.screens.main.content.shopping.ShoppingContent
import com.example.coctailapp.ui.theme.PrimaryColor
import com.example.coctailapp.ui.theme.SecondaryColor
import com.example.coctailapp.ui.theme.SemiTransparentWhite
import com.example.coctailapp.ui.theme.Typography
import kotlinx.serialization.ExperimentalSerializationApi


@OptIn(ExperimentalSerializationApi::class)
@Composable
fun MainScreen(userEmail: String, mainViewModel: MainViewModel = hiltViewModel()) {

    val navController = rememberNavController()


    val selectedBottomNavigationItem = mainViewModel.selectedBottomNavigationItem.collectAsStateWithLifecycle()
    val bottomNavigationItems = listOf(
        BottomNavigationItem(stringResource(R.string.cocktails),
            ImageVector.vectorResource(R.drawable.ic_cocktail), Destinations.CocktailsContent.serializer().descriptor.serialName),
        BottomNavigationItem(stringResource(R.string.shoppingList), Icons.Outlined.ShoppingBasket,
            Destinations.ShoppingList.serializer().descriptor.serialName),
        BottomNavigationItem(stringResource(R.string.profile), Icons.Outlined.Person,
            Destinations.Profile.serializer().descriptor.serialName)
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()


    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = PrimaryColor) {

                bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->

                    NavigationBarItem(
                        selected = bottomNavigationItem.route == navBackStackEntry?.destination?.route,
                        onClick = { mainViewModel.setSelectedBottomNavigationItem(index) },
                        icon = { Icon(bottomNavigationItem.imageVector, "") },
                        label = { Text(bottomNavigationItem.label, style = Typography.bodyLarge.copy()) },
                        colors = NavigationBarItemColors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            selectedIndicatorColor = Color.Transparent,
                            unselectedIconColor = SemiTransparentWhite,
                            unselectedTextColor = SemiTransparentWhite,
                            disabledIconColor = SemiTransparentWhite,
                            disabledTextColor = SemiTransparentWhite
                        )
                    )
                }


            }
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            SecondaryColor,
                            PrimaryColor
                        )
                    )
                )
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            BottomNavigationGraph(email = userEmail, navController, mainViewModel)


            Log.e("NavController", navController.toString())

            when(selectedBottomNavigationItem.value){
                0-> navController.navigate(Destinations.CocktailsContent){
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = false
                        saveState = false
                    }
                    launchSingleTop = true
                    restoreState = false
                }
                1-> navController.navigate(Destinations.ShoppingList){
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = false
                        saveState = false
                    }
                    launchSingleTop = true
                    restoreState = false
                }
                2-> navController.navigate(Destinations.Profile){
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = false
                        saveState = false
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            }

        }
    }

}


@Composable
fun BottomNavigationGraph(email : String, navController : NavHostController, mainViewModel: MainViewModel){

    NavHost(
        navController = navController,
        startDestination = Destinations.CocktailsContent,

    ){
        composable<Destinations.CocktailsContent> {
            CocktailsScreen(email = email, mainViewModel = mainViewModel)
        }
        composable<Destinations.ShoppingList> {
            ShoppingContent()
            mainViewModel.resetNestedNavController()
        }
        composable<Destinations.Profile> {
            ProfileContent()
            mainViewModel.resetNestedNavController()
        }

    }


}