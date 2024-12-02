package com.example.coctailapp.ui.screens.login

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.coctailapp.R
import com.example.coctailapp.model.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val sharedPreferences: SharedPreferences,
                                          @ApplicationContext val context: Context
) : ViewModel(){

    private var _loginState = MutableStateFlow<LoginEvent>(LoginEvent.LoginWait)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {


        if (email.isEmpty() || password.isEmpty()) {

            _loginState.value = LoginEvent.LoginFailed(context.getString(R.string.badInputErrorMessage))
            return
        }


        val userString = sharedPreferences.getString(email, null)

        if (userString == null) {

            _loginState.value = LoginEvent.LoginFailed(context.getString(R.string.invalidEmail))
            return


        }
        val user = Gson().fromJson(userString, User::class.java)


        if (password == user.password){
            _loginState.value = LoginEvent.LoginSuccess
        }


        else
            _loginState.value = LoginEvent.LoginFailed(context.getString(R.string.invalidPassword))

    }



fun setState(loginEvent: LoginEvent){
    _loginState.value = loginEvent
}



























}


