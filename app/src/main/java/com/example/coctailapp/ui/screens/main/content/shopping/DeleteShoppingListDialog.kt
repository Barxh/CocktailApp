package com.example.coctailapp.ui.screens.main.content.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coctailapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteShoppingListDialog(email: String, shoppingListViewModel: ShoppingListViewModel = hiltViewModel()) {

    BasicAlertDialog(
        onDismissRequest = {
            shoppingListViewModel.cancelDialog()
        }, modifier = Modifier.background(Color.White)
    ) {
        Column (modifier = Modifier.padding(20.dp)){
            Text(
                text = stringResource(R.string.deleteShoppingListDialogTitle),
                modifier = Modifier.padding(bottom = 20.dp, start = 10.dp),
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
            Text(text = stringResource(R.string.deleteShoppingListDialogMessage))


            Row (modifier = Modifier.padding(top = 30.dp)){
                Button(
                    onClick = {
                        shoppingListViewModel.cancelDialog()
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)

                ) {
                    Text(text = stringResource(R.string.cancel), textAlign = TextAlign.Center, color = Color.Black)

                }


                Button(
                    onClick = {
                        shoppingListViewModel.deleteAllFromShoppingList(userId = email)
                    },
                    modifier = Modifier.fillMaxWidth(1f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)

                ) {
                    Text(text = stringResource(R.string.yes), textAlign = TextAlign.Center, color = Color.Red)

                }
            }
        }

    }
}