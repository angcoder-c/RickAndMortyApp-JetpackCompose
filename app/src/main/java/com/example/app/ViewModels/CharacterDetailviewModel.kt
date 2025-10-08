package com.example.app.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.app.Character
import com.example.app.database.AppDatabase
import com.example.app.repositories.CharacterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CharacterDetailScreenState(
    val isLoading: Boolean = true,
    val data: Character? = null,
    val isError: Boolean = false
)

class CharacterDetailViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val characterRepository = CharacterRepository(database.characterDao())

    private val _characterDetailScreenState = MutableStateFlow(CharacterDetailScreenState())
    val characterDetailScreenState: StateFlow<CharacterDetailScreenState> = _characterDetailScreenState.asStateFlow()

    private val characterId: Int = savedStateHandle.get<Int>("characterId") ?: 0

    init {
        loadCharacter()
    }

    fun loadCharacter() {
        viewModelScope.launch {
            try {
                _characterDetailScreenState.value = CharacterDetailScreenState(isLoading = true)

                delay(4000)

                val randomNumber = (1..10).random()

                if (randomNumber % 2 == 0) {
                    val character = characterRepository.getCharacterById(characterId)
                    _characterDetailScreenState.value = CharacterDetailScreenState(
                        isLoading = false,
                        data = character,
                        isError = false
                    )
                } else {
                    _characterDetailScreenState.value = CharacterDetailScreenState(
                        isLoading = false,
                        data = null,
                        isError = true
                    )
                }
            } catch (e: Exception) {
                _characterDetailScreenState.value = CharacterDetailScreenState(
                    isLoading = false,
                    data = null,
                    isError = true
                )
            }
        }
    }
}