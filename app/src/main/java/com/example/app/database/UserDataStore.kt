package com.example.app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserDataStore(private val context: Context) {
    companion object {
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    // save username
    suspend fun saveUsername(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    fun getUsernameFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NAME_KEY]
        }
    }

    suspend fun getUsername(): String? {
        return context.dataStore.data.first()[USER_NAME_KEY]
    }

    // logout
    suspend fun clearUsername() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_NAME_KEY)
        }
    }
}