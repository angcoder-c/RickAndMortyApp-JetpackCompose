package com.example.app.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.Location
import com.example.app.database.AppDatabase
import com.example.app.database.entities.LocationEntity
import com.example.app.repositories.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LocationsScreenState(
    val isLoading: Boolean = true,
    val data: List<LocationEntity> = emptyList(),
    val isError: Boolean = false
)

class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val locationRepository = LocationRepository(database.locationDao())

    private val _locationsScreenState = MutableStateFlow(LocationsScreenState())
    val locationsScreenState: StateFlow<LocationsScreenState> = _locationsScreenState.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            try {
                _locationsScreenState.value = LocationsScreenState(isLoading = true)

                locationRepository.getAllLocations().collect { locations ->
                    _locationsScreenState.value = LocationsScreenState(
                        isLoading = false,
                        data = locations,
                        isError = false
                    )
                }
            } catch (e: Exception) {
                _locationsScreenState.value = LocationsScreenState(
                    isLoading = false,
                    data = emptyList(),
                    isError = true
                )
            }
        }
    }
}