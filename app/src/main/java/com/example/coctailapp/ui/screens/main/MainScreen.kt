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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coctailapp.R
import com.example.coctailapp.model.BottomNavigationItem
import com.example.coctailapp.ui.screens.main.content.cocktails.CocktailsContent
import com.example.coctailapp.ui.screens.main.content.profile.ProfileContent
import com.example.coctailapp.ui.screens.main.content.shopping.ShoppingContent
import com.example.coctailapp.ui.theme.PrimaryColor
import com.example.coctailapp.ui.theme.SecondaryColor
import com.example.coctailapp.ui.theme.SemiTransparentWhite
import com.example.coctailapp.ui.theme.Typography


@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {


    val selectedBottomNavigationItem = mainViewModel.selectedBottomNavigationItem.collectAsStateWithLifecycle()
    val bottomNavigationItems = listOf(
        BottomNavigationItem("Cocktails", ImageVector.vectorResource(R.drawable.ic_cocktail)),
        BottomNavigationItem("Shopping list", Icons.Outlined.ShoppingBasket),
        BottomNavigationItem("Profile", Icons.Outlined.Person)
        )

    Log.d("Icon", Icons.Outlined.ShoppingBasket.defaultWidth.toString())

    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = PrimaryColor) {

                bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
                    NavigationBarItem(
                        selected = index == selectedBottomNavigationItem.value,
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
            when(selectedBottomNavigationItem.value){
                0-> CocktailsContent()
                1-> ShoppingContent()
                2-> ProfileContent()
            }

        }
    }

}