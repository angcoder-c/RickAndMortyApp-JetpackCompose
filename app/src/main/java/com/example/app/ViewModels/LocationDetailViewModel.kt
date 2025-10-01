package com.example.app.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.Location
import com.example.app.LocationDb
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

class LocationDetailViewModel : ViewModel() {
    private val _locationDetailScreenState = MutableStateFlow(LocationDetailScreenState())
    val locationDetailScreenState: StateFlow<LocationDetailScreenState> = _locationDetailScreenState.asStateFlow()

    private val locationDb = LocationDb()

    fun loadLocation(locationId: Int) {
        viewModelScope.launch {
            _locationDetailScreenState.value = LocationDetailScreenState(isLoading = true)

            // carga - 4s
            delay(2000)
            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                _locationDetailScreenState.value = LocationDetailScreenState(
                    isLoading = false,
                    data = locationDb.getLocationById(locationId),
                    isError = false
                )
            } else {
                _locationDetailScreenState.value = LocationDetailScreenState(
                    isLoading = false,
                    data = null,
                    isError = true
                )
            }
        }
    }
}