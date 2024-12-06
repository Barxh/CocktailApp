package com.example.coctailapp.ui.screens.main.content.cocktails.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.coctailapp.R
import com.example.coctailapp.ui.components.AppThemeStyle
import com.example.coctailapp.ui.navigation.Destinations
import com.example.coctailapp.ui.screens.main.content.cocktails.CocktailsContentViewModel
import com.example.coctailapp.ui.screens.main.content.cocktails.ErrorScreen
import com.example.coctailapp.ui.screens.main.content.cocktails.LoadingScreen

@Composable
fun FilterScreenDetails(
    navController: NavController,
    cocktailsContentViewModel: CocktailsContentViewModel = hiltViewModel(),
    filterViewModel: FilterViewModel = hiltViewModel()
) {


    val fetchingState = filterViewModel.fetchingState.collectAsStateWithLifecycle()

    AppThemeStyle(
        toolbarTitle = cocktailsContentViewModel.getFilterType().toTitle(),
        toolbarNavigation = {
            IconButton(onClick = {
                navController.navigate(Destinations.FilterFragment) {
                    popUpTo(Destinations.CocktailsFragment) {
                        inclusive = false
                    }
                }
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                    null,
                    tint = Color.Black
                )
            }
        },
    ) {

        filterViewModel.fetchFilterData(cocktailsContentViewModel.getFilterType())
        when (fetchingState.value) {

            is FilterFetchingEvent.ErrorEvent -> {
                ErrorScreen((fetchingState.value as FilterFetchingEvent.ErrorEvent).errorMessage)

            }

            FilterFetchingEvent.LoadingEvent -> LoadingScreen()


            is FilterFetchingEvent.SuccessEvent -> {

                FilterDetailsDataFetchingSuccessScreen(
                    filterType = cocktailsContentViewModel.getFilterType().toTitle(),
                    list = (fetchingState.value as FilterFetchingEvent.SuccessEvent).filtersList,
                    onItemClicked = { filter ->
                        cocktailsContentViewModel.setFilter(filter)
                        navController.navigate(Destinations.CocktailsFragment) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )

            }
        }

    }

}

@Composable
fun FilterDetailsDataFetchingSuccessScreen(
    filterType: String,
    list: List<String>,
    onItemClicked: (filter: String) -> Unit
) {

    Text(
        stringResource(R.string.filterBy) + filterType,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 15.dp,
                bottom = 15.dp,
                start = 10.dp,
                end = 10.dp
            ),
        textAlign = TextAlign.Center,
        color = Color.Gray
    )
    HorizontalDivider(Modifier.fillMaxWidth(), thickness = 2.dp, color = Color.Gray)
    LazyColumn {

        list.forEachIndexed { _, filter ->

            item {
                Column {
                    Box(
                        Modifier
                            .clickable {

                                onItemClicked(filter)
                            }
                            .padding(
                                top = 15.dp,
                                bottom = 15.dp,
                                start = 25.dp,
                                end = 10.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(filter, Modifier.align(Alignment.CenterStart))


                    }
                    HorizontalDivider(
                        Modifier
                            .fillMaxWidth(),
                        thickness = 1.dp, color = Color.Black
                    )
                }


            }
        }

    }
}
