package com.example.coctailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coctailapp.ui.navigation.Destinations.LoginScreen
import com.example.coctailapp.ui.navigation.Destinations.RegisterScreen
import com.example.coctailapp.ui.screens.LoginScreen
import com.example.coctailapp.ui.screens.RegisterScreen
import com.example.coctailapp.ui.theme.CoctailAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        this.setTheme(R.style.Theme_CoctailApp)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {

            CoctailAppTheme {

                val navController = rememberNavController()

                NavHost(navController = navController,
                    startDestination = LoginScreen){
                    composable<LoginScreen> {
                        LoginScreen{
                            navController.navigate(RegisterScreen)
                        }
                    }
                    composable<RegisterScreen> {
                        RegisterScreen{
                            navController.navigate(LoginScreen){
                                popUpTo(LoginScreen){
                                    inclusive = true
                                }
                            }
                        }

                    }
                }



            }

        }

    }
}







