package com.example.coctailapp.ui.screens.main.content.cocktails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.coctailapp.R
import com.example.coctailapp.model.CocktailsPreview
import com.example.coctailapp.ui.components.AppThemeStyle
import com.example.coctailapp.ui.navigation.Destinations
import com.example.coctailapp.ui.screens.main.MainViewModel
import com.example.coctailapp.ui.screens.main.content.cocktails.CocktailsFetchingEvent.SuccessEvent
import com.example.coctailapp.ui.screens.main.content.cocktails.filter.FilterScreen
import com.example.coctailapp.ui.screens.main.content.cocktails.filter.FilterScreenDetails


@Composable
fun CocktailsScreen(mainViewModel: MainViewModel,
    cocktailsContentViewModel: CocktailsContentViewModel = hiltViewModel(),
                    ) {

    val navController = rememberNavController()

    mainViewModel.setNestedNavController(navController)

    CocktailsScreenNavigation(navController , cocktailsContentViewModel)

}


@Composable
fun LoadingScreen() {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))

    }
}

@Composable
fun ErrorScreen(errorMessage: String) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = errorMessage,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CocktailsGridScreen(cocktailsList: List<CocktailsPreview>, filter : String) {


    Column(modifier = Modifier.fillMaxSize()) {
        Text(filter, Modifier.padding(10.dp).background(Color.Green).padding(2.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Gray)
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(cocktailsList) { item: CocktailsPreview ->

                var favoriteSelected by rememberSaveable {
                    mutableStateOf(false)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(5.dp)
                        .background(Color.White)
                        .padding(5.dp)
                ) {
                    AsyncImage(
                        model = item.strDrinkThumb,
                        contentDescription = stringResource(R.string.cocktailImageDescription),
                        modifier = Modifier.height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(text = item.strDrink, textAlign = TextAlign.Center)

                    IconButton(onClick = { favoriteSelected = !favoriteSelected }) {
                        Box {
                            if (favoriteSelected) {


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

            }
        }
    }
}


@Composable
fun CocktailsPreviewingScreen(
    navController: NavHostController,
    cocktailsContentViewModel: CocktailsContentViewModel = hiltViewModel()
) {
    cocktailsContentViewModel.filterCocktails()


    val fetchingStatus = cocktailsContentViewModel.dataFetchingEvent.collectAsStateWithLifecycle()
    AppThemeStyle(
        toolbarActions = {
        IconButton(onClick = { /* to do */ }) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(R.string.search)
            )
        }
        IconButton(onClick = {
            navController.navigate(Destinations.FilterFragment){
            }
        }) {
            Icon(
                imageVector = Icons.Outlined.FilterAlt,
                contentDescription = stringResource(R.string.filter)
            )
        }
    },
        toolbarTitle = stringResource(R.string.cocktails),
        ) {

        when (fetchingStatus.value) {
            is CocktailsFetchingEvent.ErrorEvent -> {
                ErrorScreen((fetchingStatus.value as CocktailsFetchingEvent.ErrorEvent).errorMessage)
                cocktailsContentViewModel.retryCocktailsPreviewFetching()
            }

            CocktailsFetchingEvent.LoadingEvent -> LoadingScreen()
            is SuccessEvent -> CocktailsGridScreen(
                (fetchingStatus.value as SuccessEvent).cocktailsList, cocktailsContentViewModel.getFilter()
            )
        }

    }

}
@Composable
fun CocktailsScreenNavigation(navController: NavHostController,
                              cocktailsContentViewModel: CocktailsContentViewModel){

    NavHost(
        navController= navController,
        startDestination = Destinations.CocktailsFragment){
        composable<Destinations.CocktailsFragment> {
            CocktailsPreviewingScreen(navController, cocktailsContentViewModel)
        }
        composable<Destinations.FilterFragment> {
            FilterScreen(navController,cocktailsContentViewModel)
        }
        composable<Destinations.FilterDetailsFragment> {
            FilterScreenDetails(navController, cocktailsContentViewModel)
        }
        composable<Destinations.SearchFragment> {

        }
    }

}




