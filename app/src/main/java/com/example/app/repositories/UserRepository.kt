package com.example.app.repositories

import com.example.app.datastore.UserDataStore
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userPreferences: UserDataStore
) {

    suspend fun saveUserName(name: String) {
        userPreferences.saveUsername(name)
    }

    fun getUserNameFlow(): Flow<String?> {
        return userPreferences.getUsernameFlow()
    }

    suspend fun getUserName(): String? {
        return userPreferences.getUsername()
    }

    suspend fun logout() {
        userPreferences.clearUsername()
    }
}