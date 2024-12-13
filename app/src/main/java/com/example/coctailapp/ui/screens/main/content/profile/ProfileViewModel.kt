package com.example.coctailapp.ui.screens.main.content.profile

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.example.coctailapp.model.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
import android.util.Base64
import androidx.lifecycle.viewModelScope
import com.example.coctailapp.Constants
import com.example.coctailapp.database.FavoritesCocktailsDao
import com.example.coctailapp.model.localdb.UserFavoriteCocktail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val contentResolver: ContentResolver,
    private val favoritesCocktailsDao: FavoritesCocktailsDao
) : ViewModel() {

    private var _userData = MutableStateFlow(User(email = "", password = ""))
    val userData = _userData.asStateFlow()

    private lateinit var userFavoritesListFlow: Flow<List<UserFavoriteCocktail>>
    private var _userFavoritesList = MutableStateFlow<List<UserFavoriteCocktail>>(emptyList())
    val userFavoritesList = _userFavoritesList.asStateFlow()

    private var _logoutDialog = MutableStateFlow<LogoutDialogEvent>(LogoutDialogEvent.HideDialog)
    val logoutDialog = _logoutDialog.asStateFlow()

    private var _changeNameDialog = MutableStateFlow<ChangeNameDialogEvent>(ChangeNameDialogEvent.HideDialog)
    val changeNameDialog = _changeNameDialog.asStateFlow()

    private var _changeNameStatus = MutableStateFlow<ChangeNameStatus>(ChangeNameStatus.RequestPending)
    val changeNameStatus = _changeNameStatus.asStateFlow()



    fun setCurrentUser(email: String) {
        _userData.value = Gson().fromJson(sharedPreferences.getString(email, ""), User::class.java)
        getUserFavorites(email)
    }


    fun saveUserImage(uri: Uri?) {
        if (uri != null &&  Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            val source = ImageDecoder.createSource(contentResolver, uri)
            val bitmap = ImageDecoder.decodeBitmap(source)
            _userData.value = _userData.value.copy(image = encodeBitmapToBase64(bitmap))
            val userString = Gson().toJson(_userData.value)
            sharedPreferences.edit {
                putString(_userData.value.email, userString)
                apply()
            }
        }
    }


    private fun encodeBitmapToBase64(bitmap: Bitmap): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(
            Bitmap.CompressFormat.PNG,
            100,
            byteArrayOutputStream
        )
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val decodedByteArray = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
    }

    fun deleteUserFavorites(favoriteCocktail: UserFavoriteCocktail) {
        viewModelScope.launch {
            favoritesCocktailsDao.deleteFavoriteCocktail(favoriteCocktail)
            userFavoritesListFlow.collectLatest {

                _userFavoritesList.value = it
            }
        }
    }
    private fun getUserFavorites(email: String){
        userFavoritesListFlow = favoritesCocktailsDao.getUserFavoritesCocktails(email)
        viewModelScope.launch {
            userFavoritesListFlow.collectLatest {

                _userFavoritesList.value = it
            }
        }
    }

    fun cancelLogoutDialog() {
        _logoutDialog.value = LogoutDialogEvent.HideDialog
    }

    fun logout() {
        sharedPreferences.edit {
            putString(Constants.LOGGED_USER, "")
            apply()
        }
        _logoutDialog.value = LogoutDialogEvent.LogoutEvent
    }

    fun showLogoutDialog() {
        _logoutDialog.value = LogoutDialogEvent.ShowDialog
    }

    fun showChangeNameDialog() {
        _changeNameDialog.value = ChangeNameDialogEvent.ShowDialog
    }
    fun cancelChangeNameDialog(){
        _changeNameDialog.value = ChangeNameDialogEvent.HideDialog
    }

    fun getUsername(): String {
        return userData.value.name
    }

    fun changeUsername(username: String) {

        if (username.isEmpty()){
            _changeNameDialog.value = ChangeNameDialogEvent.HideDialog
            _changeNameStatus.value = ChangeNameStatus.InputErrorEvent("Please enter some name")
        }else if (username == userData.value.name){
            _changeNameDialog.value = ChangeNameDialogEvent.HideDialog
            _changeNameStatus.value = ChangeNameStatus.InputErrorEvent("Please enter different name from you have now")
        }else{
            _userData.value = _userData.value.copy(name = username)
            sharedPreferences.edit {
                putString(_userData.value.email, Gson().toJson(_userData.value))
                apply()
            }
            _changeNameDialog.value = ChangeNameDialogEvent.HideDialog
        }
    }

    fun resetChangePasswordStatus() {
        _changeNameStatus.value = ChangeNameStatus.RequestPending
    }


}