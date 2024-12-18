package com.example.coctailapp.ui.screens.main.content.profile

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.imageLoader
import coil3.memory.MemoryCache
import com.example.coctailapp.Constants
import com.example.coctailapp.R
import com.example.coctailapp.database.FavoritesCocktailsDao
import com.example.coctailapp.model.User
import com.example.coctailapp.model.localdb.UserFavoriteCocktail
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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

    private var _userImage = MutableStateFlow<String?>(null)
    val userImage = _userImage.asStateFlow()

    private var _logoutDialog = MutableStateFlow<LogoutDialogEvent>(LogoutDialogEvent.HideDialog)
    val logoutDialog = _logoutDialog.asStateFlow()

    private var _changeNameDialog =
        MutableStateFlow<ChangeNameDialogEvent>(ChangeNameDialogEvent.HideDialog)
    val changeNameDialog = _changeNameDialog.asStateFlow()

    private var _changeNameStatus =
        MutableStateFlow<ChangeNameStatus>(ChangeNameStatus.RequestPending)
    val changeNameStatus = _changeNameStatus.asStateFlow()


    fun setCurrentUser(email: String) {
        _userData.value = Gson().fromJson(sharedPreferences.getString(email, ""), User::class.java)
        getUserFavorites(email)
        getUserImage()
    }


    @SuppressLint("Recycle")
    fun saveUserImage(uri: Uri?) {
        if (uri != null) {
            try {

                context.imageLoader.memoryCache?.remove(MemoryCache.Key(userImage.value?: ""))
                val inputStream = contentResolver.openInputStream(uri)
                inputStream.let {
                    val fileDir = context.filesDir
                    val userImageDir = File(fileDir, userData.value.email)
                    if(!userImageDir.exists()){
                        userImageDir.mkdirs()
                    }

                    userImageDir.listFiles()?.forEach { imgFile ->
                        imgFile.delete()
                    }

                    val cacheFile = File(userImageDir, "${System.currentTimeMillis()}.jpg")

                    val outputStream = FileOutputStream(cacheFile)
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (inputStream!!.read(buffer).also { length = it } != -1) {
                        outputStream.write(buffer, 0, length)
                    }


                    inputStream.close()
                    outputStream.close()
                    _userImage.update { _ ->
                        cacheFile.path
                    }
                }

            } catch (exception: IOException) {
                Log.e("Save image error", exception.toString())
            }
        }
    }


    private fun getUserImage() {
        val fileDir = context.filesDir
        val userImageDir = File(fileDir, userData.value.email)
        if(!userImageDir.exists()){
            userImageDir.mkdirs()
        }

        val imgFile = userImageDir.listFiles()?.firstOrNull()
        imgFile?.let {
            _userImage.value = it.path
        }
    }

    fun deleteUserFavorites(favoriteCocktail: UserFavoriteCocktail) {
        viewModelScope.launch {
            favoritesCocktailsDao.deleteFavoriteCocktail(favoriteCocktail)
            userFavoritesListFlow.collectLatest {

                _userFavoritesList.value = it
            }
        }
    }

    private fun getUserFavorites(email: String) {
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

    fun cancelChangeNameDialog() {
        _changeNameDialog.value = ChangeNameDialogEvent.HideDialog
    }

    fun getUsername(): String {
        return userData.value.name
    }

    fun changeUsername(username: String) {

        if (username.isEmpty()) {
            _changeNameDialog.value = ChangeNameDialogEvent.HideDialog
            _changeNameStatus.value =
                ChangeNameStatus.InputErrorEvent(context.getString(R.string.enterSomeName))
        } else if (username == userData.value.name) {
            _changeNameDialog.value = ChangeNameDialogEvent.HideDialog
            _changeNameStatus.value =
                ChangeNameStatus.InputErrorEvent(context.getString(R.string.enterDifferentName))
        } else {
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