package com.example.coctailapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coctailapp.R
import com.example.coctailapp.ui.screens.components.CustomTextField




@Composable
fun LoginScreen(navigateToRegisterScreen: ()->Unit) {
    val color = 0x7736682C

    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF36682C),
                            Color(0XFFBDE8D5)

                        ),
                        start = Offset(0f, 1200f),
                        end = Offset(0f,0f)
                    )
                )
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = innerPadding.calculateTopPadding() + 40.dp,
                    bottom = innerPadding.calculateBottomPadding() + 40.dp
                )

        ) {

            Row {
                Text(
                    "Lo",
                    fontSize = 28.sp
                )
                Text(
                    "Gin",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(15.dp))

            CustomTextField(email, "email", color, false, KeyboardType.Email)

            Spacer(Modifier.height(15.dp))


            CustomTextField(password, "password", color, true , KeyboardType.Password)

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = {


                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .width(140.dp)
                    .height(44.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_login),
                        contentDescription = null,

                        tint = Color(0xFF36682C)
                    )
                    Text(
                        text = "Login",
                        color = Color(0xFF36682C),
                        fontSize = 21.sp
                    )
                }
            }
            Spacer(Modifier.height(15.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                HorizontalDivider(thickness = 1.dp, color = Color.White, modifier = Modifier.weight(0.4f))
                Text(
                    text = "or", color = Color.White, modifier = Modifier.weight(0.2f), textAlign = TextAlign.Center
                )
                HorizontalDivider(thickness = 1.dp, color = Color.White, modifier = Modifier.weight(0.4f))

            }

            Spacer(Modifier.height(15.dp))

            Button(
                shape = RectangleShape,
                onClick = {
                    navigateToRegisterScreen()

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x11FFFFFF),
                )
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .width(124.dp)
                        .height(44.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_register),
                        contentDescription = null,

                        tint = Color.White
                    )
                    Text(
                        text = "Register",
                        color = Color.White,
                        fontSize = 21.sp
                    )


                }

            }
            Spacer(Modifier.height(50.dp))

            Image(
                painter = painterResource(R.drawable.conctal_gin),
                contentDescription = "Photo of gin tonic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(121.dp)
                    .height(175.dp)
            )


        }

    }

}