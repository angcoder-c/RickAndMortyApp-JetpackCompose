/*
* Angel Gabriel Chavez Otzoy
* 24248
* 30/09/2025
* */

package com.example.app.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.app.CharacterDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.app.Character

data class CharactersScreenState (
    val isLoading: Boolean = true,
    val data: List<Character> = emptyList(),
    val isError: Boolean = false
)

class CharactersViewModel : ViewModel() {
    private val _charactersScreenState = MutableStateFlow<CharactersScreenState>(CharactersScreenState())

    // flujo de datos
    val charactersScreenState: StateFlow<CharactersScreenState> = _charactersScreenState.asStateFlow()
    private val characterDb = CharacterDb()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _charactersScreenState.value = CharactersScreenState(isLoading = true)

            // carga - 4s
            delay(4000)

            // random number 1-10
            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                _charactersScreenState.value = CharactersScreenState(
                    isLoading = false,
                    data = characterDb.getAllCharacters(),
                    isError = false
                )
            } else {
                _charactersScreenState.value = CharactersScreenState(
                    isLoading = false,
                    data = emptyList(),
                    isError = true
                )
            }
        }
    }
}