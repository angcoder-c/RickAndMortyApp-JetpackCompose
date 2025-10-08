package com.example.app.repositories

import com.example.app.Character
import com.example.app.CharacterDb
import com.example.app.database.dao.CharacterDao
import com.example.app.mappers.toEntity
import com.example.app.mappers.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(
    private val characterDao: CharacterDao,
    private val characterDb: CharacterDb = CharacterDb()
) {
    // CharacterDb to Room
    suspend fun syncCharacters() {
        val characters = characterDb.getAllCharacters()
        val entities = characters.map {
            it.toEntity()
        }
        characterDao.insertAll(entities)
    }

    // characters from Room
    fun getAllCharacters(): Flow<List<Character>> {
        return characterDao.getAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

    // characters by id from room
    suspend fun getCharacterById(id: Int): Character? {
        return characterDao.getById(id)?.toModel()
    }
}