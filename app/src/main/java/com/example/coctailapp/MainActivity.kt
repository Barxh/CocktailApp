package com.example.coctailapp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.coctailapp.ui.navigation.Destinations.LoginScreen
import com.example.coctailapp.ui.navigation.Destinations.MainScreen
import com.example.coctailapp.ui.navigation.Destinations.RegisterScreen
import com.example.coctailapp.ui.screens.login.LoginScreen
import com.example.coctailapp.ui.screens.main.MainScreen
import com.example.coctailapp.ui.screens.register.RegisterScreen
import com.example.coctailapp.ui.theme.CoctailAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        this.setTheme(R.style.Theme_CoctailApp)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CoctailAppTheme {

                val navController = rememberNavController()

                val loggedUser = sharedPreferences.getString(Constants.LOGGED_USER, "")

                NavHost(
                    navController = navController,
                    startDestination =
                    if (loggedUser!!.isEmpty()) LoginScreen
                    else MainScreen(
                        loggedUser
                    )
                ) {
                    composable<LoginScreen> {
                        LoginScreen(
                            navigateToRegisterScreen = { navController.navigate(RegisterScreen) },

                            navigateToMainScreen = { email ->
                                navController.navigate(MainScreen(email)) {
                                    popUpTo(LoginScreen) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                    composable<RegisterScreen> {
                        RegisterScreen(
                            navigateToLoginScreen = {
                                navController.navigate(LoginScreen) {
                                    popUpTo(LoginScreen) {
                                        inclusive = true
                                    }
                                }
                            },
                            navigateToMainScreen = { email ->
                                navController.navigate(MainScreen(email)) {
                                    popUpTo(LoginScreen) {
                                        inclusive = true
                                    }
                                }
                            }
                        )

                    }

                    composable<MainScreen> {

                        val args = it.toRoute<MainScreen>()
                        MainScreen(userEmail = args.email, {
                            navController.navigate(LoginScreen) {
                                popUpTo<MainScreen>() {
                                    inclusive = true
                                }
                            }
                        })
                    }
                }

            }


        }

    }

}








