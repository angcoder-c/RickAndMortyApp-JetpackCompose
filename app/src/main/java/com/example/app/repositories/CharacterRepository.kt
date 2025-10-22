package com.example.app.repositories

import com.example.app.Character
import com.example.app.database.dao.CharacterDao
import com.example.app.database.entities.CharacterEntity
import com.example.app.http.ApiClient
import com.example.app.network.ApiService
import com.example.app.network.mappers.toCharacterEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CharacterRepository(private val characterDao: CharacterDao) {
    private val apiService = ApiService(ApiClient)

    fun getAllCharacters(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacters()
    }

    suspend fun getCharacterById(id: Int): CharacterEntity? {
        return characterDao.getCharacterById(id)
    }

    suspend fun syncCharacters() {
        try {
            val existingCharacters = characterDao.getAllCharacters().first()

            // offline first
            if (existingCharacters.isNotEmpty()) {
                return
            }

            // si no hay datos locales, obtenerlos del api
            val response = apiService.getCharacters(page = 1)
            val characters = response.results.toCharacterEntities()
            characterDao.insertAll(characters)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }
}