package com.example.app.repositories

import com.example.app.datastore.UserDataStore
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDataStore: UserDataStore) {
    suspend fun saveUserName(userName: String) {
        userDataStore.saveUserName(userName)
    }

    suspend fun getUserName(): String? {
        return userDataStore.getUserName()
    }

    fun getUserNameFlow(): Flow<String?> {
        return userDataStore.getUserNameFlow()
    }

    suspend fun logout() {
        userDataStore.clearUserName()
    }
}