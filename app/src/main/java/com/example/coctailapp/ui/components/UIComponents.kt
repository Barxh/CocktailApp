package com.example.coctailapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.coctailapp.R
import com.example.coctailapp.ui.theme.PrimaryColor
import com.example.coctailapp.ui.theme.SecondaryColor
import com.example.coctailapp.ui.theme.TertiaryColor
import com.example.coctailapp.ui.theme.Typography


@Composable
fun CustomTextField(
    state: MutableState<String>, label: String, color: Color ,
    isPasswordField: Boolean, keyboardType: KeyboardType, isError: Boolean
) {

    var passwordVisible by remember {
        mutableStateOf(false)
    }


    TextField(

        value = state.value,
        onValueChange = {  state.value = it },
        label = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = label,
                    style = Typography.bodyLarge.copy(),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }, visualTransformation =
        if (isPasswordField && !passwordVisible)
            PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {
            if (isPasswordField) {

                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible)
                    stringResource(R.string.trailingIconDescriptionHide)
                else stringResource(R.string.trailingIconDescriptionShow)

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(imageVector = image, contentDescription = description, tint = Color.Gray)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldColors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
            errorTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.Gray,
            errorContainerColor = Color.White,
            cursorColor = color,
            errorCursorColor = Color.Gray,
            textSelectionColors = TextSelectionColors(
                handleColor = color,
                backgroundColor = Color.White
            ),
            focusedIndicatorColor = color,
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
            focusedLabelColor = color,
            unfocusedLabelColor = Color.Gray,
            disabledLabelColor = Color.White,
            errorLabelColor = Color.Red,
            focusedPlaceholderColor = color,
            unfocusedPlaceholderColor = Color.Gray,
            disabledPlaceholderColor = Color.Gray,
            errorPlaceholderColor = Color.Red,
            focusedSupportingTextColor = Color.Black,
            unfocusedSupportingTextColor = Color.Black,
            disabledSupportingTextColor = Color.White,
            errorSupportingTextColor = Color.Red,
            focusedPrefixColor = Color.White,
            unfocusedPrefixColor = Color.Black,
            disabledPrefixColor = Color.White,
            errorPrefixColor = Color.Red,
            focusedSuffixColor = Color.White,
            unfocusedSuffixColor = Color.Black,
            disabledSuffixColor = Color.White,
            errorSuffixColor = Color.Red
        ),
        isError = isError
    )


}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppThemeStyle(
    toolbarTitle: String,
    toolbarActions: @Composable () -> Unit = {},
    toolbarNavigation: @Composable () -> Unit = {},
    snackBarHostState: SnackbarHostState? = null,
    content: @Composable () -> Unit


) {


    Scaffold(
        snackbarHost = {
            if (snackBarHostState!=null)
                SnackbarHost(snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = toolbarTitle,
                        style = Typography.bodyLarge,
                        fontSize = TextUnit(22f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarColors(
                    containerColor = TertiaryColor,
                    scrolledContainerColor = TertiaryColor,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ),
                actions = {
                        toolbarActions()
                },
                navigationIcon = {
                        toolbarNavigation()
                }
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
                            PrimaryColor,
                            SecondaryColor
                        ),
                        start = Offset(0f, 1000f),
                        end = Offset(0f, 200f)
                    )
                )

        ) {
            content()

        }

    }
}