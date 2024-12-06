package com.example.coctailapp.ui.screens.main.content.cocktails.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavHostController
import com.example.coctailapp.Constants
import com.example.coctailapp.R
import com.example.coctailapp.ui.components.AppThemeStyle
import com.example.coctailapp.ui.navigation.Destinations
import com.example.coctailapp.ui.screens.main.content.cocktails.CocktailsContentViewModel


@Composable
fun FilterScreen(
    navController: NavHostController,
    cocktailsContentViewModel: CocktailsContentViewModel = hiltViewModel()
) {


    FilterTypeChoosingScreen(
        title = stringResource(R.string.filter),
        onNavigationButtonClicked = {
            navController.navigate(Destinations.CocktailsFragment)
            {
                popUpTo(Destinations.CocktailsFragment) {
                    inclusive = true
                }
            }
        },
        filterList = Constants.FILTER_OPTIONS,
        onItemClicked = { filterType ->
            cocktailsContentViewModel.setFilterType(filterType)
            navController.navigate(Destinations.FilterDetailsFragment)

        }
    )



}


@Composable
fun FilterTypeChoosingScreen(
    title: String,
    onNavigationButtonClicked: () -> Unit,
    filterList: Array<FilterType>,
    onItemClicked: (filterType: FilterType) -> Unit,
) {

    AppThemeStyle(
        toolbarTitle = title,
        toolbarNavigation = {
            IconButton(onClick = {
                onNavigationButtonClicked()
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                    null,
                    tint = Color.Black
                )
            }
        },
    ) {
        Text(
            stringResource(R.string.filterBy),
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

            items(filterList) { filterType ->
                Column {
                    Box(
                        Modifier
                            .clickable {

                                onItemClicked(filterType)
                            }
                            .padding(
                                top = 15.dp,
                                bottom = 15.dp,
                                start = 25.dp,
                                end = 10.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(filterType.toTitle(), Modifier.align(Alignment.CenterStart))
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_arrow_forward), null,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )

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

