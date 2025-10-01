package com.example.app.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.Character
import com.example.app.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CharacterDetailScreenState (
    val isLoading: Boolean = true,
    val data: Character? = null,
    val isError: Boolean = false
)

class CharacterDetailViewModel: ViewModel() {
    private val _characterDetailScreenState = MutableStateFlow<CharacterDetailScreenState>(CharacterDetailScreenState())

    // flujo de datos
    val characterDetailScreenState: StateFlow<CharacterDetailScreenState> = _characterDetailScreenState.asStateFlow()
    private val characterDb = CharacterDb()

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _characterDetailScreenState.value = CharacterDetailScreenState(isLoading = true)

            // carga - 4s
            delay(4000)

            // random number 1-10
            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                _characterDetailScreenState.value = CharacterDetailScreenState(
                    isLoading = false,
                    data = characterDb.getCharacterById(characterId),
                    isError = false
                )
            } else {
                _characterDetailScreenState.value = CharacterDetailScreenState(
                    isLoading = false,
                    data = null,
                    isError = true
                )
            }
        }
    }
}