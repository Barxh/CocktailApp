package com.example.coctailapp.ui.screens.main.content.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.coctailapp.R
import com.example.coctailapp.model.localdb.UserFavoriteCocktail
import com.example.coctailapp.ui.navigation.Destinations
import com.example.coctailapp.ui.screens.main.content.cocktails.cocktails_details.CocktailsDetailsScreen
import com.example.coctailapp.ui.theme.PrimaryColor
import com.example.coctailapp.ui.theme.SecondaryColor
import com.example.coctailapp.ui.theme.SemiTransparentWhite
import com.example.coctailapp.ui.theme.Typography


@Composable
fun ProfileContent(email: String, logout: () -> Unit) {

    val navHostController = rememberNavController()

    ProfileNavigation(email, navHostController, logout)


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFragment(
    email: String,
    navHostController: NavHostController,
    logout: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val snackBarHostState = SnackbarHostState()

    profileViewModel.setCurrentUser(email)
    val currentUserData = profileViewModel.userData.collectAsStateWithLifecycle()
    val userFavoritesList = profileViewModel.userFavoritesList.collectAsStateWithLifecycle()
    val logoutDialog = profileViewModel.logoutDialog.collectAsStateWithLifecycle()
    val changeNameDialog = profileViewModel.changeNameDialog.collectAsStateWithLifecycle()
    val changeNameStatus = profileViewModel.changeNameStatus.collectAsStateWithLifecycle()
    val userImage by profileViewModel.userImage.collectAsStateWithLifecycle()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            profileViewModel.saveUserImage(uri)
        }
    when (changeNameDialog.value) {
        ChangeNameDialogEvent.HideDialog -> {
            //Do nothing
        }

        ChangeNameDialogEvent.ShowDialog -> {
            ChangeNameDialog()
        }
    }
    when (logoutDialog.value) {
        LogoutDialogEvent.HideDialog -> {
            //Do nothing
        }

        LogoutDialogEvent.ShowDialog -> {
            LogoutDialog()
        }

        LogoutDialogEvent.LogoutEvent -> {
            logout()
        }
    }

    LaunchedEffect(changeNameStatus.value) {
        when (changeNameStatus.value) {
            is ChangeNameStatus.InputErrorEvent -> {
                snackBarHostState.showSnackbar((changeNameStatus.value as ChangeNameStatus.InputErrorEvent).errorMessage)
                profileViewModel.resetChangePasswordStatus()
            }

            ChangeNameStatus.RequestPending -> {
                // Do nothing
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                stringResource(R.string.myProfile),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(
                    22f,
                    TextUnitType.Sp,

                    ),
                style = Typography.bodyLarge
            )
        },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SecondaryColor),
            actions = {
                Row {
                    IconButton(onClick = {

                        profileViewModel.showChangeNameDialog()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_edit),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = {

                        profileViewModel.showLogoutDialog()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_logout),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }

        )
    }, snackbarHost = {
        SnackbarHost(snackBarHostState)
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .background(
                    Brush.linearGradient(
                        listOf(
                            PrimaryColor, SecondaryColor
                        ), start = Offset(0f, 1000f), end = Offset(0f, 200f)
                    )
                )
        ) {
            Row(Modifier.padding(10.dp)) {


                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userImage)
                        .build(),
                    error = painterResource(R.drawable.person_placeholder),
                    placeholder = painterResource(R.drawable.person_placeholder),
                    contentDescription = stringResource(R.string.userImage),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(start = 50.dp, top = 15.dp)) {
                    Text(currentUserData.value.name, fontSize = TextUnit(18f, TextUnitType.Sp))
                    Text(
                        currentUserData.value.email,
                        fontSize = TextUnit(16f, TextUnitType.Sp),
                        modifier = Modifier.padding(top = 15.dp),
                        color = Color.Gray
                    )
                }


            }
            HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Gray)
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(userFavoritesList.value) { item: UserFavoriteCocktail ->


                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(5.dp)
                            .background(SemiTransparentWhite)
                            .padding(5.dp)
                            .clickable {
                                navHostController.navigate(Destinations.CocktailsDetailsScreen(item.idDrink))
                            }) {
                        AsyncImage(
                            model = item.imageURL,
                            contentDescription = stringResource(R.string.cocktailImageDescription),
                            modifier = Modifier.height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(text = item.cocktailName, textAlign = TextAlign.Center)

                        IconButton(onClick = {
                            profileViewModel.deleteUserFavorites(
                                item
                            )
                        }) {
                            Box {

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


                            }
                        }


                    }

                }
            }

        }


    }
}

@Composable
fun ProfileNavigation(email: String, navHostController: NavHostController, logout: () -> Unit) {

    NavHost(navHostController, Destinations.ProfileFragment) {
        composable<Destinations.ProfileFragment> {
            ProfileFragment(email, navHostController, logout)
        }
        composable<Destinations.CocktailsDetailsScreen> {
            val args = it.toRoute<Destinations.CocktailsDetailsScreen>()
            CocktailsDetailsScreen(email = email,
                cocktailId = args.cocktailId,
                backButtonNavigation = {
                    navHostController.navigate(Destinations.ProfileFragment) {
                        popUpTo(Destinations.ProfileFragment) {
                            inclusive = true
                        }
                    }
                })
        }
    }
}