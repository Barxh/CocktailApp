package com.example.coctailapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.coctailapp.R
import com.example.coctailapp.RegisterScreen
import com.example.coctailapp.ui.screens.Font.fontFamily

object Font {
    val fontFamily = FontFamily(
        Font(R.font.caveat_bold, FontWeight.Bold),
        Font(R.font.caveat_medium, FontWeight.Medium),
        Font(R.font.caveat_regular, FontWeight.Normal),
        Font(R.font.caveat_semibold, FontWeight.SemiBold)
    )
}


@Composable
fun loginScreen(navController: NavHostController) {
    val color = 0x7736682C

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
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
                    fontFamily = fontFamily,
                    fontSize = 28.sp
                )
                Text(
                    "Gin",
                    fontFamily = fontFamily,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(15.dp))

            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "email",
                            fontFamily = fontFamily,
                            modifier = Modifier.align(Alignment.Center) // Centriranje labele
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .width(41.dp),
                colors =TextFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Gray,
                    errorTextColor = Color.Red,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    errorContainerColor = Color.Red,
                    cursorColor = Color(0x2236682C),
                    errorCursorColor = Color.Red,
                    textSelectionColors = TextSelectionColors(
                        handleColor = Color(color),
                        backgroundColor = Color.White
                    ),
                    focusedIndicatorColor = Color(color),
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.Gray,
                    errorIndicatorColor = Color.Red,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    disabledLeadingIconColor = Color.White,
                    errorLeadingIconColor = Color.White,
                    focusedTrailingIconColor = Color.White,
                    unfocusedTrailingIconColor = Color.White,
                    disabledTrailingIconColor = Color.White,
                    errorTrailingIconColor = Color.White,
                    focusedLabelColor = Color(color),
                    unfocusedLabelColor = Color.Gray,
                    disabledLabelColor = Color.White,
                    errorLabelColor = Color.Red,
                    focusedPlaceholderColor = Color(color),
                    unfocusedPlaceholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray,
                    errorPlaceholderColor = Color.Red,
                    focusedSupportingTextColor = Color.Black,
                    unfocusedSupportingTextColor = Color.Black,
                    disabledSupportingTextColor = Color.White,
                    errorSupportingTextColor = Color.White,
                    focusedPrefixColor = Color.White,
                    unfocusedPrefixColor = Color.Black,
                    disabledPrefixColor = Color.White,
                    errorPrefixColor = Color.White,
                    focusedSuffixColor = Color.White,
                    unfocusedSuffixColor = Color.Black,
                    disabledSuffixColor = Color.White,
                    errorSuffixColor = Color.White
                )
            )

            Spacer(Modifier.height(15.dp))


            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Box(Modifier.fillMaxWidth()){
                        Text("password", fontFamily = fontFamily, modifier = Modifier.align(
                            Alignment.Center))
                    }
                     },
                modifier = Modifier.fillMaxWidth(),
                colors =TextFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Gray,
                    errorTextColor = Color.Red,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    errorContainerColor = Color.Red,
                    cursorColor = Color(0x2236682C),
                    errorCursorColor = Color.Red,
                    textSelectionColors = TextSelectionColors(
                        handleColor = Color(color),
                        backgroundColor = Color.White
                    ),
                    focusedIndicatorColor = Color(color),
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.Gray,
                    errorIndicatorColor = Color.Red,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    disabledLeadingIconColor = Color.White,
                    errorLeadingIconColor = Color.White,
                    focusedTrailingIconColor = Color.White,
                    unfocusedTrailingIconColor = Color.White,
                    disabledTrailingIconColor = Color.White,
                    errorTrailingIconColor = Color.White,
                    focusedLabelColor = Color(color),
                    unfocusedLabelColor = Color.Gray,
                    disabledLabelColor = Color.White,
                    errorLabelColor = Color.Red,
                    focusedPlaceholderColor = Color(color),
                    unfocusedPlaceholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray,
                    errorPlaceholderColor = Color.Red,
                    focusedSupportingTextColor = Color.Black,
                    unfocusedSupportingTextColor = Color.Black,
                    disabledSupportingTextColor = Color.White,
                    errorSupportingTextColor = Color.White,
                    focusedPrefixColor = Color.White,
                    unfocusedPrefixColor = Color.Black,
                    disabledPrefixColor = Color.White,
                    errorPrefixColor = Color.White,
                    focusedSuffixColor = Color.White,
                    unfocusedSuffixColor = Color.Black,
                    disabledSuffixColor = Color.White,
                    errorSuffixColor = Color.White
                )
                
            )

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
                        fontFamily = fontFamily,
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
                Spacer(
                    Modifier
                        .width(129.dp)
                        .height(1.dp)
                        .background(Color.White)
                )
                Text(
                    text = "or",
                    fontFamily = fontFamily, color = Color.White
                )
                Spacer(
                    Modifier
                        .width(129.dp)
                        .height(1.dp)
                        .background(Color.White)
                )
            }

            Spacer(Modifier.height(15.dp))

            Button(
                shape = RectangleShape,
                onClick = {
                    navController.navigate(RegisterScreen)

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
                        fontFamily = fontFamily,
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