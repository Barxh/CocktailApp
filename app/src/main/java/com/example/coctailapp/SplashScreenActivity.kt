package com.example.coctailapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.coctailapp.ui.theme.CoctailAppTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            installSplashScreen()

        }
        setContent {
            CoctailAppTheme {

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q)
                    SplashScreen()


            }
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

@Composable
fun SplashScreen() {

    LaunchedEffect(key1 = true, block = {
        delay(2000)
    })

    Box(
        Modifier
            .fillMaxSize()

            .background(Color(0xFFEA9922))
    ) {

        Icon(
            painter = painterResource(R.drawable.ic_coctail),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    CoctailAppTheme {
        SplashScreen()
    }
}