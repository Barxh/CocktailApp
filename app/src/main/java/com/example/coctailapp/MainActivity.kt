package com.example.coctailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coctailapp.ui.screens.LoginScreen
import com.example.coctailapp.ui.screens.RegisterScreen
import com.example.coctailapp.ui.theme.CoctailAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
//    private var splash = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        installSplashScreen().apply {
//            setKeepOnScreenCondition {
//                splash
//            }
//        }
        setContent {

            CoctailAppTheme {

                val navController = rememberNavController()

                NavHost(navController = navController,
                    startDestination = LoginScreen){
                    composable<LoginScreen> {
                        LoginScreen(){
                            navController.navigate(RegisterScreen)
                        }
                    }
                    composable<RegisterScreen> {
                        RegisterScreen(){
                            navController.navigate(LoginScreen){
                                popUpTo(LoginScreen){
                                    inclusive = true
                                }
                            }
                        }

                    }
                }



            }

//            splash = false
        }

    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    Icon(
        painter = painterResource(R.drawable.ic_coctail),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}

@Serializable
object LoginScreen

@Serializable
object RegisterScreen
