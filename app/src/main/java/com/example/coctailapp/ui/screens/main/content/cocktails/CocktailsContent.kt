package com.example.coctailapp.ui.screens.main.content.cocktails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import com.example.coctailapp.R
import com.example.coctailapp.model.CocktailsPreviewPlusFavorites
import com.example.coctailapp.ui.components.AppThemeStyle
import com.example.coctailapp.ui.navigation.Destinations
import com.example.coctailapp.ui.screens.main.MainViewModel
import com.example.coctailapp.ui.screens.main.content.cocktails.CocktailsFetchingEvent.SuccessEvent
import com.example.coctailapp.ui.screens.main.content.cocktails.cocktails_details.CocktailsDetailsScreen
import com.example.coctailapp.ui.screens.main.content.cocktails.filter.FilterScreen
import com.example.coctailapp.ui.screens.main.content.cocktails.filter.FilterScreenDetails
import com.example.coctailapp.ui.screens.main.content.cocktails.search.SearchScreen
import com.example.coctailapp.ui.theme.SemiTransparentGreen
import kotlinx.coroutines.launch


@Composable
fun CocktailsScreen(
    email: String,
    mainViewModel: MainViewModel,
    cocktailsContentViewModel: CocktailsContentViewModel = hiltViewModel(),
) {
    cocktailsContentViewModel.setCurrentUserEmail(email)

    val navController = rememberNavController()
    mainViewModel.setNestedHostNavController(navController)

    CocktailsScreenNavigation(navController, cocktailsContentViewModel, email)
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
            IconButton(onClick = { navController.navigate(Destinations.SearchFragment) }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }
            IconButton(onClick = {
                navController.navigate(Destinations.FilterFragment) {
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

        var isRefreshing by remember {
            mutableStateOf(false)
        }

        when (fetchingStatus.value) {
            is CocktailsFetchingEvent.ErrorEvent -> {
                ErrorScreen((fetchingStatus.value as CocktailsFetchingEvent.ErrorEvent).errorMessage, isRefreshing
                ) { cocktailsContentViewModel.filterCocktails() }
            }

            CocktailsFetchingEvent.LoadingEvent -> {
                isRefreshing = false
                LoadingScreen()

            }
            is SuccessEvent -> CocktailsGridScreen(
                cocktailsContentViewModel.getFilter(),
                cocktailsContentViewModel,
                navController
            )
        }

    }

}


@Composable
fun LoadingScreen() {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorScreen(
    errorMessage: String,
    refreshing: Boolean = false,
    onRefresh :()->Unit = {},
) {

    //TODO(Uradi Error skrin za filter i za cocktail details)
    val pullToRefreshState = rememberPullToRefreshState()


    var isRefreshing by remember {
        mutableStateOf(refreshing)
    }
    val scope = rememberCoroutineScope()
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        onRefresh = {
            isRefreshing = true

            scope.launch{
                pullToRefreshState.animateToHidden()
            }

            isRefreshing = false
            onRefresh()


        },
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
            item{

                    Text(
                        text = errorMessage,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .padding(20.dp),
                        textAlign = TextAlign.Center
                    )

            }
        }


    }
}

@Composable
fun CocktailsGridScreen(
    filter: String,
    cocktailsContentViewModel: CocktailsContentViewModel,
    navController: NavHostController
) {

    val cocktailsPreviewPlusFavoritesList =
        cocktailsContentViewModel.cocktailsPreviewPlusFavorites.collectAsStateWithLifecycle()


    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            filter,
            Modifier
                .padding(10.dp)
                .background(SemiTransparentGreen)
                .padding(
                    top = 2.dp,
                    bottom = 2.dp,
                    start = 10.dp,
                    end = 10.dp
                )
        )

        HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Gray)
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(cocktailsPreviewPlusFavoritesList.value) { item: CocktailsPreviewPlusFavorites ->


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(5.dp)
                        .background(Color.White)
                        .padding(5.dp)
                        .clickable {
                            navController.navigate(Destinations.CocktailsDetailsScreen(item.idDrink))
                        }
                ) {
                    AsyncImage(
                        model = item.imageURL,
                        contentDescription = stringResource(R.string.cocktailImageDescription),
                        modifier = Modifier.height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(text = item.cocktailName, textAlign = TextAlign.Center)

                    IconButton(onClick = {
                        cocktailsContentViewModel.deleteOrInsertToUserFavorites(
                            cocktailsPreviewPlusFavorites = item
                        )
                    }) {
                        Box {
                            if (item.favorite) {


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
fun CocktailsScreenNavigation(
    navController: NavHostController,
    cocktailsContentViewModel: CocktailsContentViewModel,
    email: String
) {

    NavHost(
        navController = navController,
        startDestination = Destinations.CocktailsFragment
    ) {
        composable<Destinations.CocktailsFragment> {
            CocktailsPreviewingScreen(navController, cocktailsContentViewModel)
        }
        composable<Destinations.FilterFragment> {
            FilterScreen(navController, cocktailsContentViewModel)
        }
        composable<Destinations.FilterDetailsFragment> {
            FilterScreenDetails(navController, cocktailsContentViewModel)
        }
        composable<Destinations.SearchFragment> {

            SearchScreen(cocktailsContentViewModel, navController)
        }
        composable<Destinations.CocktailsDetailsScreen> {
            val args = it.toRoute<Destinations.CocktailsDetailsScreen>()
            CocktailsDetailsScreen(email, args.cocktailId, {
                navController.navigate(Destinations.CocktailsFragment) {

                    popUpTo(Destinations.CocktailsFragment) {
                        inclusive = true
                    }
                }
            })
        }
    }

}




