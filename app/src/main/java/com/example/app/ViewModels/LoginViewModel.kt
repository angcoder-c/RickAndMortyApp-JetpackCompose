package com.example.app.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.database.AppDatabase
import com.example.app.datastore.UserDataStore
import com.example.app.repositories.CharacterRepository
import com.example.app.repositories.LocationRepository
import com.example.app.repositories.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val userPreferences = UserDataStore(application)

    private val userRepository = UserRepository(userPreferences)
    private val characterRepository = CharacterRepository(database.characterDao())
    private val locationRepository = LocationRepository(database.locationDao())

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val userName = userRepository.getUserName()
            _loginState.value = LoginState(isLoggedIn = userName != null)
        }
    }

    fun login(name: String) {
        if (name.isBlank()) {
            _loginState.value = LoginState(error = "Ingresar nombre de usuario: ")
            return
        }

        viewModelScope.launch {
            try {
                _loginState.value = LoginState(isLoading = true)
                userRepository.saveUserName(name)
                characterRepository.syncCharacters()
                locationRepository.syncLocations()
                _loginState.value = LoginState(
                    isLoading = false,
                    isLoggedIn = true
                )
            } catch (e: Exception) {
                _loginState.value = LoginState(
                    isLoading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }
}