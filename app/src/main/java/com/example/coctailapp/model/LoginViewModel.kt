package com.example.coctailapp.model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) : ViewModel(){

    private var _loginState = MutableStateFlow<LoginEvent>(LoginEvent.LoginWait)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {


        if (email.isEmpty() || password.isEmpty()) {

            _loginState.value = LoginEvent.LoginFailed("Please enter email and password")
            return
        }


        val userString = sharedPreferences.getString(email, null)

        if (userString == null) {

            _loginState.value = LoginEvent.LoginFailed("Invalid email")
            return


        }
        val user = Gson().fromJson(userString, User::class.java)


        if (password == user.password){
            _loginState.value = LoginEvent.LoginSuccess
        }

        else
            _loginState.value = LoginEvent.LoginFailed("Invalid password")

    }



fun setState(loginEvent: LoginEvent){
    _loginState.value = loginEvent
}



























}


