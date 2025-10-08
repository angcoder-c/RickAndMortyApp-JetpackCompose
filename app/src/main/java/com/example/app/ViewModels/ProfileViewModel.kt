package com.example.app.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.datastore.UserDataStore
import com.example.app.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileState(
    val userName: String = "",
    val isLoggingOut: Boolean = false
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userPreferences = UserDataStore(application)
    private val userRepository = UserRepository(userPreferences)

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    init {
        loadUserName()
    }

    private fun loadUserName() {
        viewModelScope.launch {
            userRepository.getUserNameFlow().collect { name ->
                _profileState.value = ProfileState(userName = name ?: "")
            }
        }
    }

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            _profileState.value = _profileState.value.copy(isLoggingOut = true)
            userRepository.logout()
            onLogoutComplete()
        }
    }
}