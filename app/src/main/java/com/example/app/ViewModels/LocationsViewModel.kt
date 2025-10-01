package com.example.app.ViewModels

import com.example.app.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LocationsScreenState(
    val isLoading: Boolean = true,
    val data: List<Location> = emptyList(),
    val isError: Boolean = false
)

class LocationsViewModel : ViewModel() {
    private val _locationsScreenState = MutableStateFlow(LocationsScreenState())
    val locationsScreenState: StateFlow<LocationsScreenState> = _locationsScreenState.asStateFlow()

    private val locationDb = LocationDb()

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            _locationsScreenState.value = LocationsScreenState(isLoading = true)

            // carga - 4s
            delay(4000)
            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                _locationsScreenState.value = LocationsScreenState(
                    isLoading = false,
                    data = locationDb.getAllLocations(),
                    isError = false
                )
            } else {
                _locationsScreenState.value = LocationsScreenState(
                    isLoading = false,
                    data = emptyList(),
                    isError = true
                )
            }
        }
    }
}