package com.example.app.repositories

import com.example.app.Location
import com.example.app.database.dao.LocationDao
import com.example.app.database.entities.LocationEntity
import com.example.app.http.ApiClient
import com.example.app.network.ApiService
import com.example.app.network.mappers.toLocationEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class LocationRepository(
    private val locationDao: LocationDao
) {
    private val apiService = ApiService(ApiClient)
    /**
     * ubicaciones desde la base de datos local
     */
    fun getAllLocations(): Flow<List<LocationEntity>> {
        return locationDao.getAllLocations()
    }

    /**
     * ubicacion por id desde la base de datos local
     */
    suspend fun getLocationById(id: Int): LocationEntity? {
        return locationDao.getLocationById(id)
    }

    /**
     * sincroniza ubicaciones desde el api y las guarda en Room
     */
    suspend fun syncLocations() {
        try {
            // verificar si ya tenemos datos locales
            val existingLocations = locationDao.getAllLocations().first()

            // offline first
            if (existingLocations.isNotEmpty()) {
                return
            }

            // si no hay datos locales, obtenerlos del api
            val response = apiService.getLocations(page=1)
            val locations = response.results.toLocationEntities()
            locationDao.insertAll(locations)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }
}