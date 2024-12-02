package com.example.coctailapp.ui.screens.register

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
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
class RegisterViewModel @Inject constructor(private val sharedPreferences: SharedPreferences,
    @ApplicationContext val context: Context) :
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

            _registerState.value = RegisterEvent.RegistrationFailed(context.getString(R.string.badInputErrorMessage))
            return
        }

        if (!Regex(emailRegex).matches(email)) {

            _registerState.value =
                RegisterEvent.RegistrationFailed(context.getString(R.string.invalidEmail))
            _emailError.value = true
            return
        }

        if (!Regex(passwordRegex).matches(password)) {
            _registerState.value = RegisterEvent.RegistrationFailed(context.getString(R.string.invalidPatternPassword)
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