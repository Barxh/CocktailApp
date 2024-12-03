package com.example.coctailapp.ui.screens.main.content.cocktails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.coctailapp.R
import com.example.coctailapp.model.CocktailsPreview
import com.example.coctailapp.ui.theme.PrimaryColor
import com.example.coctailapp.ui.theme.SecondaryColor
import com.example.coctailapp.ui.theme.TertiaryColor
import com.example.coctailapp.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailsContent(
    cocktailsContentViewModel: CocktailsContentViewModel = hiltViewModel()
) {

    val cocktailsList = cocktailsContentViewModel.cocktailsList.collectAsStateWithLifecycle()
    val fetchingStatus = cocktailsContentViewModel.dataFetchingEvent.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.cocktails),
                        style = Typography.bodyLarge,
                        fontSize = TextUnit(22f, TextUnitType.Sp)
                    )
                },
                actions = {

                    IconButton(
                        onClick = { /* to do */}
                    ) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = stringResource(R.string.search))

                    }
                    IconButton(onClick = {/*TO DO*/}) {
                        Icon(imageVector = Icons.Outlined.FilterAlt, contentDescription = stringResource(R.string.filter))
                    }
                },
                colors = TopAppBarColors(
                    containerColor = TertiaryColor,
                    scrolledContainerColor = TertiaryColor,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            SecondaryColor,
                            PrimaryColor
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 1500f)
                    )
                )

        ){

            when(fetchingStatus.value){
                is CocktailsFetchingEvent.ErrorEvent -> {
                    ErrorScreen((fetchingStatus.value as CocktailsFetchingEvent.ErrorEvent).errorMessage)
                }
                CocktailsFetchingEvent.LoadingEvent -> LoadingScreen()
                CocktailsFetchingEvent.SuccessEvent -> CocktailsListScreen(cocktailsList.value)
            }
        }
    }
}

@Composable
fun LoadingScreen(){
    Box(Modifier.fillMaxSize()){
        CircularProgressIndicator(Modifier.align(Alignment.Center))

    }
}

@Composable
fun ErrorScreen(errorMessage: String){
    Box (Modifier.fillMaxSize()){
        Text(text = errorMessage, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun CocktailsListScreen(cocktailsList: List<CocktailsPreview>){




    Column (modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(cocktailsList) { item: CocktailsPreview ->

                var favoriteSelected by rememberSaveable {
                    mutableStateOf(false)
                }
                    Column (horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(5.dp)
                            .background(Color.White)
                            .padding(5.dp)){
                        AsyncImage(
                            model = item.strDrinkThumb,
                            contentDescription = stringResource(R.string.cocktailImageDescription),
                            modifier = Modifier.height(250.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(text=item.strDrink, textAlign = TextAlign.Center )

                        IconButton(onClick = { favoriteSelected = !favoriteSelected}) {
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
                                }else{
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






