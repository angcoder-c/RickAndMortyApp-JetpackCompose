package com.example.app.repositories

import com.example.app.Location
import com.example.app.LocationDb
import com.example.app.database.dao.LocationDao
import com.example.app.mappers.toEntity
import com.example.app.mappers.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepository(
    private val locationDao: LocationDao,
    private val locationDb: LocationDb = LocationDb()
) {

    // LocationDb from room
    suspend fun syncLocations() {
        val locations = locationDb.getAllLocations()
        val entities = locations.map { it.toEntity() }
        locationDao.insertAll(entities)
    }

    // locations from room
    fun getAllLocations(): Flow<List<Location>> {
        return locationDao.getAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

    // locations by id from room
    suspend fun getLocationById(id: Int): Location? {
        return locationDao.getById(id)?.toModel()
    }
}