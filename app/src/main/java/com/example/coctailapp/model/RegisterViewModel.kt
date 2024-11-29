package com.example.coctailapp.model

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel() {


    private val _registerState = MutableStateFlow<RegisterEvent>(RegisterEvent.RegistrationAwait)
    val registerState = _registerState.asStateFlow()

    private val _emailError = MutableStateFlow(false)
    val emailError = _emailError.asStateFlow()
    private val _passwordError = MutableStateFlow(false)
    val passwordError = _passwordError.asStateFlow()

    fun registerUser(name: String, email: String, password: String) {

        val passwordRegex = "^(?=.+[A-Za-z])(?=.+\\d)(?=.+[@$!%*#?&.])[A-Za-z\\d@$!%*#?&.]{8,}$"
        val emailRegex = "^[A-Za-z0-9$!%*#?&.]+@[A-Za-z0-9]+\\.[a-z]+$"

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {

            _registerState.value = RegisterEvent.RegistrationFailed("Please populate all fields")
            return
        }

        if (!Regex(emailRegex).matches(email)) {

            _registerState.value =
                RegisterEvent.RegistrationFailed("Invalid email! Please enter valid email.")
            _emailError.value = true
            return
        }

        if (!Regex(passwordRegex).matches(password)) {
            _registerState.value = RegisterEvent.RegistrationFailed(
                "Please enter password with minimum one letter, minimum one number, minimum one special character" +
                        " that has more than eight characters"
            )
            _emailError.value = false
            _passwordError.value = true
            return
        }

        if (!(sharedPreferences.getString(email, null)).isNullOrEmpty()) {
            _registerState.value =
                RegisterEvent.RegistrationFailed("Account with this email already exist")
            _emailError.value = true
            _passwordError.value = false
            return

        }
        _emailError.value = false
        _passwordError.value = false


        val user = User(email, password, name)

        val gsonUser = Gson().toJson(user)
        sharedPreferences.edit {
            putString(email, gsonUser)
            apply()

        }
        _registerState.value = RegisterEvent.RegistrationSuccessful
    }

    fun setRegistrationState(registerEvent: RegisterEvent) {
        _registerState.value = registerEvent
    }


}