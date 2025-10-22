package com.example.app.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.Character
import com.example.app.database.AppDatabase
import com.example.app.database.entities.CharacterEntity
import com.example.app.repositories.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CharactersScreenState(
    val isLoading: Boolean = true,
    val data: List<CharacterEntity> = emptyList(),
    val isError: Boolean = false
)

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val characterRepository = CharacterRepository(database.characterDao())

    private val _charactersScreenState = MutableStateFlow(CharactersScreenState())
    val charactersScreenState: StateFlow<CharactersScreenState> = _charactersScreenState.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            try {
                _charactersScreenState.value = CharactersScreenState(isLoading = true)

                characterRepository.getAllCharacters().collect { characters ->
                    _charactersScreenState.value = CharactersScreenState(
                        isLoading = false,
                        data = characters,
                        isError = false
                    )
                }
            } catch (e: Exception) {
                _charactersScreenState.value = CharactersScreenState(
                    isLoading = false,
                    data = emptyList(),
                    isError = true
                )
            }
        }
    }
}