package com.example.coctailapp.ui.screens.main.content.cocktails.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.coctailapp.R
import com.example.coctailapp.ui.components.AppThemeStyle
import com.example.coctailapp.ui.components.CustomTextField
import com.example.coctailapp.ui.navigation.Destinations
import com.example.coctailapp.ui.screens.main.content.cocktails.CocktailsContentViewModel
import com.example.coctailapp.ui.screens.main.content.cocktails.filter.FilterType
import com.example.coctailapp.ui.theme.Typography

@Composable
fun SearchScreen(viewModel: CocktailsContentViewModel, navController: NavController) {

    viewModel.setFilterType(FilterType.SEARCH)
    val search = remember {
        mutableStateOf("")
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val searchRegulationState = viewModel.searchRegulationState.collectAsStateWithLifecycle()

    LaunchedEffect(searchRegulationState.value) {
        when (searchRegulationState.value) {
            SearchRegulation.SearchApproved -> {
                navController.navigate(Destinations.CocktailsFragment) {
                    popUpTo(Destinations.CocktailsFragment) {
                        inclusive = true
                    }

                }
                viewModel.resetRegulationState()
            }

            is SearchRegulation.SearchDenied -> {
                snackBarHostState.showSnackbar(
                    (searchRegulationState.value as SearchRegulation.SearchDenied).message
                )
                viewModel.resetRegulationState()
            }

            SearchRegulation.SearchPending -> {
                //Do nothing
            }
        }
    }
    AppThemeStyle(
        toolbarTitle = stringResource(R.string.search),
        toolbarActions = {},
        toolbarNavigation = {

            IconButton(onClick = {
                navController.navigate(Destinations.CocktailsFragment) {
                    popUpTo(Destinations.CocktailsFragment) {
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back), null, tint = Color.Black)

            }


        },
        snackBarHostState = snackBarHostState
    ) {
        Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(20.dp))
            CustomTextField(search, stringResource(R.string.search), Color.White, false, KeyboardType.Text, false)
            Spacer(Modifier.height(50.dp))
            Button(
                onClick = {

                    viewModel.setSearchFilter(search.value)
                }, modifier = Modifier
                    .width(140.dp)
                    .height(44.dp),
                shape = RectangleShape
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                    Text(stringResource(R.string.search), style = Typography.bodyLarge)
                }
            }
        }

    }
}


