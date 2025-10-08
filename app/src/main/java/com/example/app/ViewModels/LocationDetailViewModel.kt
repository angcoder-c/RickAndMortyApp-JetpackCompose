package com.example.app.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.app.Location
import com.example.app.database.AppDatabase
import com.example.app.repositories.LocationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LocationDetailScreenState(
    val isLoading: Boolean = true,
    val data: Location? = null,
    val isError: Boolean = false
)

class LocationDetailViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val locationRepository = LocationRepository(database.locationDao())

    private val _locationDetailScreenState = MutableStateFlow(LocationDetailScreenState())
    val locationDetailScreenState: StateFlow<LocationDetailScreenState> = _locationDetailScreenState.asStateFlow()

    private val locationId: Int = savedStateHandle.get<Int>("locationsId") ?: 0

    init {
        loadLocation()
    }

    fun loadLocation() {
        viewModelScope.launch {
            try {
                _locationDetailScreenState.value = LocationDetailScreenState(isLoading = true)

                delay(4000)

                val randomNumber = (1..10).random()

                if (randomNumber % 2 == 0) {
                    val location = locationRepository.getLocationById(locationId)
                    _locationDetailScreenState.value = LocationDetailScreenState(
                        isLoading = false,
                        data = location,
                        isError = false
                    )
                } else {
                    _locationDetailScreenState.value = LocationDetailScreenState(
                        isLoading = false,
                        data = null,
                        isError = true
                    )
                }
            } catch (e: Exception) {
                _locationDetailScreenState.value = LocationDetailScreenState(
                    isLoading = false,
                    data = null,
                    isError = true
                )
            }
        }
    }
}