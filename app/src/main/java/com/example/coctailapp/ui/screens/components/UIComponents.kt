package com.example.coctailapp.ui.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.coctailapp.ui.theme.Typography


@Composable
fun CustomTextField(
    state: MutableState<String>, label: String, color: Int,
    isPasswordField: Boolean, keyboardType: KeyboardType
) {

    var passwordVisible by remember {
        mutableStateOf(false)
    }


    TextField(

        value = state.value,
        onValueChange = { it -> state.value = it },
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
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(imageVector = image,contentDescription=  description, tint = Color.Gray)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .width(41.dp),
        colors = TextFieldColors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
            errorTextColor = Color.Red,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.Gray,
            errorContainerColor = Color.Red,
            cursorColor = Color(color),
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


}