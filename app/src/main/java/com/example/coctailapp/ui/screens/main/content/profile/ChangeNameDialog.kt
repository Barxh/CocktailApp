package com.example.coctailapp.ui.screens.main.content.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChangeNameDialog(profileViewModel: ProfileViewModel = hiltViewModel()) {

    var username by remember {
        mutableStateOf(profileViewModel.getUsername())
    }


    Dialog(
        onDismissRequest = {
            profileViewModel.cancelChangeNameDialog()
        }
    ) {
        Column (modifier = Modifier.background(Color.White).padding(20.dp)){
            Text(
                text = "Change Name",
                modifier = Modifier.padding(bottom = 20.dp, start = 10.dp),
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
            TextField(username, {
                username = it
            })


            Row (modifier = Modifier.padding(top = 30.dp)){
                Button(
                    onClick = {
                        profileViewModel.cancelChangeNameDialog()
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)

                ) {
                    Text(text = "Cancel", textAlign = TextAlign.Center, color = Color.Black)

                }


                Button(
                    onClick = {
                        profileViewModel.changeUsername(username)
                    },
                    modifier = Modifier.fillMaxWidth(1f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)

                ) {
                    Text(text = "Submit", textAlign = TextAlign.Center, color = Color.Red)

                }
            }
        }

    }
}